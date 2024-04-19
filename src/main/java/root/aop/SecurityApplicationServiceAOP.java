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
import root.repository.ResumeRepo;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Aspect
@Component
@Slf4j
public class SecurityApplicationServiceAOP {
    private final AccountRepo accountRepo;
    private final ResumeRepo resumeRepo;
    private final ApplicationRepo applicationRepo;

    /**
     * Chỉ company sở hữu CV(đã apply) mới có quyền xem contact của CV
     */
    @Before("execution(* root.service.ApplicationService.getUserContactByResumeId(*))")
    public void getUserContactByResumeId(JoinPoint joinPoint) {
        long resumeId = (long) joinPoint.getArgs()[0]; // array các tham số

        if (!resumeRepo.existsById(resumeId)) {
            throw new ResourceNotFoundException("resume with id [" + resumeId + "] not found");
        }

        String usernameSecurity = getUsernameSecurityContextHolder();
        List<String> roles = getRolesSecurityContextHolder();

        Optional<Account> currentCompanyAccount = accountRepo.findByUsername(usernameSecurity);
        Long currentCompanyAccountId = currentCompanyAccount.get().getId();

        if (roles != null && roles.contains("COMPANY")) {
            Optional<Application> application = applicationRepo.findApplicationByResumeIdAndCompanyId(
                resumeId,
                currentCompanyAccountId
            );
            if (application.isEmpty()) {
                throw new ForbiddenException("Forbidden AOP: Company does not own a Resume");
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
