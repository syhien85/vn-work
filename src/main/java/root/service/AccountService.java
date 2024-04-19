package root.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import root.dto.AccountDTO;
import root.dto.PageDTO;
import root.dto.SearchDTO;
import root.entity.Account;
import root.entity.Role;
import root.exception.ResourceNotFoundException;
import root.repository.AccountRepo;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class AccountService implements UserDetailsService {

    private final AccountRepo accountRepo;
    private final EmailService emailService;

    @Transactional
    public void create(AccountDTO accountDTO) {
        Account account = new ModelMapper().map(accountDTO, Account.class);

        account.setPassword(new BCryptPasswordEncoder().encode(account.getPassword()));
        accountRepo.save(account);
    }

    @Transactional
    public void update(AccountDTO accountDTO) {
        Account currentAccount = accountRepo.findById(accountDTO.getId()).orElseThrow(
            () -> new ResourceNotFoundException(
                "account with id [" + accountDTO.getId() + "] not found"
            )
        );
        currentAccount.setUsername(accountDTO.getUsername());

        accountRepo.save(currentAccount);
    }

    @Transactional
    public void updatePassword(AccountDTO accountDTO) {
        Account currentAccount = accountRepo.findById(accountDTO.getId()).orElseThrow(
            () -> new ResourceNotFoundException(
                "account with id [" + accountDTO.getId() + "] not exists"
            )
        );

        String passwordEncoderNew = new BCryptPasswordEncoder().encode(accountDTO.getPassword());
        if (accountDTO.getPassword() != null && !passwordEncoderNew.equals(currentAccount.getPassword())) {
            currentAccount.setPassword(passwordEncoderNew);
        }

        accountRepo.save(currentAccount);
    }

    @Transactional
    public void forgotPassword(String usernameOrEmail) {
        Optional<Account> currentAccount = accountRepo.findByUsernameOrEmail(usernameOrEmail);
        if (currentAccount.isEmpty()) {
            throw new ResourceNotFoundException(
                "account with username or email [" + usernameOrEmail + "] not exists"
            );
        }

        // generate password
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 6;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String passwordGenerated = buffer.toString();

        String bodyEmail =
            "<p>Hi " + currentAccount.get().getUsername() + ",</p>" +
                "<p>Use your secret code!</p>" +
                "<h3><b>" + passwordGenerated + "</b></h3>" +
                "<p>If you did not forget your password, you can ignore this email.</p>";

        emailService.sendMail(
            currentAccount.get().getEmail(),
            "Password retrieval - Project 3 Springboot",
            bodyEmail
        );

        String passwordEncoder = new BCryptPasswordEncoder().encode(passwordGenerated);

        currentAccount.get().setPassword(passwordEncoder);

        accountRepo.save(currentAccount.get());
    }

    @Transactional
    public void delete(Long id) {
        if (!accountRepo.existsById(id)) {
            throw new ResourceNotFoundException("account with id [" + id + "] not found");
        }
        accountRepo.deleteById(id);
    }

    public AccountDTO getById(Long id) {
        return accountRepo.findById(id).map(this::convert).orElseThrow(
            () -> new ResourceNotFoundException("account with id [" + id + "] not found")
        );
    }

    public PageDTO<AccountDTO> searchService(SearchDTO searchDTO) {
        Sort sortBy = Sort.by("id").descending();

        if (searchDTO.getSortedField() != null && !searchDTO.getSortedField().isEmpty()) {
            sortBy = Sort.by(searchDTO.getSortedField()).ascending();
        }

        PageRequest pageRequest = PageRequest.of(
            searchDTO.getCurrentPage(), searchDTO.getSize(), sortBy
        );

        Page<Account> page = accountRepo.searchByUsername(
            "%" + searchDTO.getKeyword() + "%", pageRequest
        );

        return PageDTO.<AccountDTO>builder()
            .totalPage(page.getTotalPages())
            .totalElements(page.getTotalElements())
            .data(page.get().map(this::convert).toList())
            .build();
    }

    private AccountDTO convert(Account account) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(account, AccountDTO.class);
    }

    // Security
    /*@Transactional*/
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> accountEntity = accountRepo.findByUsername(username);
        if (accountEntity.isEmpty()) {
            throw new UsernameNotFoundException("Not Found Username");
        }
        // convert User -> UserDetails
        // chuyển role về quyền(authority) trong security
        List<SimpleGrantedAuthority> authorities = accountEntity.get().getRoles()
            .stream()
            .map((Role role) -> new SimpleGrantedAuthority(role.getName()))
            .toList();

        // User này là con của UserDetails
        return new org.springframework.security.core.userdetails.User(
            username,
            accountEntity.get().getPassword(),
            authorities
        );
    }
}

