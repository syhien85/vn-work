package root.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import root.dto.AccountDTO;
import root.dto.RefreshTokenDTO;
import root.dto.ResponseDTO;
import root.entity.Account;
import root.repository.AccountRepo;
import root.security.RefreshTokenService;

import java.security.Principal;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class LoginController {

    private final RefreshTokenService refreshTokenService;
    private final AccountRepo accountRepo;

    @PostMapping("/login")
    public ResponseDTO<RefreshTokenDTO> login(String username, String password) {
        return ResponseDTO.<RefreshTokenDTO>builder()
            .status(200).msg("OK")
            .data(refreshTokenService.login(username, password))
            .build();
    }

    @PostMapping("/refresh-token")
    public ResponseDTO<RefreshTokenDTO> refreshToken(String refreshToken) {
        return ResponseDTO.<RefreshTokenDTO>builder()
            .status(200).msg("OK")
            .data(refreshTokenService.refreshToken(refreshToken))
            .build();
    }

    // method security
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/me")
    /*public Principal me(Principal principal) {
        // principal:: current user đang đăng nhập
        // String username = principal.getName();
        return principal;
    }*/
    public AccountDTO me(Principal principal) {
        Optional<Account> account = accountRepo.findByUsername(principal.getName());
        return account.map(this::convert).orElse(null);
    }

    private AccountDTO convert(Account account) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(account, AccountDTO.class);
    }
}

