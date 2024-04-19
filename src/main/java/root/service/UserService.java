package root.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import root.dto.*;
import root.entity.*;
import root.exception.ResourceNotFoundException;
import root.repository.UserRepo;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepo userRepo;

    @Transactional
    public void create(UserDTO userDTO) {
        User user = new ModelMapper().map(userDTO, User.class);

        // set role = User before save
        Account account = user.getAccount();
        account.setRoles(List.of(Role.builder().id(2).build()));

        account.setPassword(new BCryptPasswordEncoder().encode(account.getPassword()));

        user.setAccount(account);

        userRepo.save(user);
    }

    @Transactional
    public void update(UserDTO userDTO) {
        User currentUser = userRepo.findById(userDTO.getAccount().getId()).orElseThrow(
            () -> new ResourceNotFoundException(
                "user with id [" + userDTO.getAccount().getId() + "] not found"
            )
        );

        AccountDTO accountDTO = userDTO.getAccount();

        // set role = User before save
        accountDTO.setRoles(List.of(Role.builder().id(2).build()));
        accountDTO.setPassword(new BCryptPasswordEncoder().encode(accountDTO.getPassword()));

        currentUser.setAccount(
            new ModelMapper().map(accountDTO, Account.class)
        );

        /*currentUser.setUserContact(
            new ModelMapper().map(userDTO.getUserContact(), UserContact.class)
        );*/

        userRepo.save(currentUser);
    }

    @Transactional
    public void delete(Long id) {
        if (!userRepo.existsById(id)) {
            throw new ResourceNotFoundException("user with id [" + id + "] not found");
        }
        userRepo.deleteById(id);
    }

    public UserDTO getById(Long id) {
        return userRepo.findById(id).map(this::convert).orElseThrow(
            () -> new ResourceNotFoundException("user with id [" + id + "] not found")
        );
    }

    public PageDTO<UserDTO> searchService(SearchDTO searchDTO) {
        Sort sortBy = Sort.by("id").descending();

        if (searchDTO.getSortedField() != null && !searchDTO.getSortedField().isEmpty()) {
            sortBy = Sort.by(searchDTO.getSortedField()).ascending();
        }

        PageRequest pageRequest = PageRequest.of(
            searchDTO.getCurrentPage(), searchDTO.getSize(), sortBy
        );

        Page<User> page = userRepo.searchByName(
            "%" + searchDTO.getKeyword() + "%", pageRequest
        );

        return PageDTO.<UserDTO>builder()
            .totalPage(page.getTotalPages())
            .totalElements(page.getTotalElements())
            .data(page.get().map(this::convert).toList())
            .build();
    }

    private UserDTO convert(User user) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(user, UserDTO.class);
    }
}

