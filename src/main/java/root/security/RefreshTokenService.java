package root.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import root.dto.RefreshTokenDTO;
import root.entity.Account;
import root.entity.RefreshToken;
import root.exception.ForbiddenException;
import root.exception.ResourceNotFoundException;
import root.repository.AccountRepo;
import root.repository.RefreshTokenRepo;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static java.time.temporal.ChronoUnit.DAYS;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {
    private final RefreshTokenRepo refreshTokenRepo;
    private final AccountRepo accountRepo;
    private final JwtTokenService jwtTokenService;
    private final AuthenticationManager authenticationManager;

    private static final long refreshExpiration = 30;

    public RefreshTokenDTO login(String username, String password) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(username, password)
        );

        String accessToken = jwtTokenService.createToken(username);

        Account account = accountRepo.findByUsername(username)
            .orElseThrow(() ->
                new ResourceNotFoundException("Username is not in database!")
            );

        Optional<RefreshToken> currentRefreshToken = refreshTokenRepo.findByAccountId(account.getId());

        RefreshToken newRefreshRefreshToken = null;
        // nếu refreshToken có trong DB
        if (currentRefreshToken.isPresent()) {
            newRefreshRefreshToken = currentRefreshToken.get();
            // nếu refreshToken trong DB hết hạn, xoá refreshToken, tạo mới refreshToken
            if (verifyExpiration(currentRefreshToken.get())) {
                newRefreshRefreshToken = createRefreshToken(account.getId());
            }
        } else {
            newRefreshRefreshToken = createRefreshToken(account.getId());
        }

        return new RefreshTokenDTO(accessToken, newRefreshRefreshToken.getToken());
    }

    public RefreshTokenDTO refreshToken(String refreshToken) {
        RefreshToken currentRefreshToken = refreshTokenRepo.findByToken(refreshToken)
            .orElseThrow(() ->
                new ResourceNotFoundException("Refresh token is not in database!")
            );
        // verify refreshToken, nếu hết hạn thì xoá trong DB và throw exception
        if (verifyExpiration(currentRefreshToken)) {
            throw new ForbiddenException(
                "Refresh token was expired. Please make a new sign in request!"
            );
        }

        String accessToken = jwtTokenService.createToken(currentRefreshToken.getAccount().getUsername());

        return new RefreshTokenDTO(accessToken, refreshToken);
    }

    private RefreshToken createRefreshToken(Long accountId) {
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setAccount(accountRepo.findById(accountId).get());
        refreshToken.setExpired(Date.from(Instant.now().plus(refreshExpiration, DAYS)));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepo.save(refreshToken);
        return refreshToken;
    }

    private boolean verifyExpiration(RefreshToken refreshToken) {
        if (refreshToken.getExpired().before(Date.from(Instant.now()))) {
            refreshTokenRepo.delete(refreshToken); // refreshToken hết hạn
            return true;
        }
        return false;
    }
}
