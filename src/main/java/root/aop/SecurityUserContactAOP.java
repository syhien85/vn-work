package root.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import root.entity.Account;
import root.entity.Application;
import root.exception.ForbiddenException;
import root.exception.ResourceNotFoundException;
import root.repository.AccountRepo;
import root.repository.ApplicationRepo;
import root.repository.UserContactRepo;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Aspect
@Component
@Slf4j
public class SecurityUserContactAOP {
    private final AccountRepo accountRepo;
    private final UserContactRepo userContactRepo;
    private final ApplicationRepo applicationRepo;

    @Before("execution(* root.service.UserContactService.getById(*))")
    public void getById(JoinPoint joinPoint) {
        long idUserContact = (long) joinPoint.getArgs()[0]; // array các tham số

        if (!userContactRepo.existsById(idUserContact)) {
            throw new ResourceNotFoundException("userContact with id [" + idUserContact + "] not found");
        }

        Long accountIdOfUserContact =
            userContactRepo.findById(idUserContact).get().getUser().getAccountId();

        String usernameSecurity = getUsernameSecurityContextHolder();
        List<String> roles = getRolesSecurityContextHolder();

        Optional<Account> currentAccount = accountRepo.findByUsername(usernameSecurity);
        Long currentAccountId = currentAccount.get().getId();

        /*if (roles != null && roles.contains("COMPANY")) {
            Optional<Application> application = applicationRepo.findApplicationByUserIdAndCompanyId(
                accountIdOfUserContact,
                currentAccountId
            );
            if (application.isEmpty()) {
                throw new ForbiddenException("Forbidden Company AOP");
            }
        }*/

        if (roles != null && roles.contains("USER")) {
            if (!currentAccountId.equals(accountIdOfUserContact)) {
                throw new ForbiddenException("Forbidden User AOP");
            }
        }
    }

    private static List<String> getRolesSecurityContextHolder() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            List<String> roles = authentication
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
            return roles;
        }
        return null;
    }

    private static String getUsernameSecurityContextHolder() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return authentication.getName();
        }
        return null;
    }
}
