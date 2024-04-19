package root.aop;

import root.dto.AccountDTO;
import root.exception.ForbiddenException;
import root.exception.ResourceNotFoundException;
import root.repository.AccountRepo;
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

import java.util.List;

@RequiredArgsConstructor
@Aspect
@Component
@Slf4j
public class SecurityAccountAOP {
    private final AccountRepo accountRepo;

    @Before("execution(* root.service.AccountService.update(*))")
    public void update(JoinPoint joinPoint) {
        AccountDTO accountDTO = (AccountDTO) joinPoint.getArgs()[0]; // array các tham số
        if (!accountRepo.existsById(accountDTO.getId())) {
            throw new ResourceNotFoundException("account with id [" + accountDTO.getId() + "] not found");
        }
        String usernameRequest = accountRepo.findById(accountDTO.getId()).get().getUsername();
        String usernameSecurity = getUsernameSecurityContextHolder();

        List<String> roles = getRolesSecurityContextHolder();
        // Chỉ username nào sở hữu account.id đó và role ADMIN, còn lại bị cấm
        if (roles == null || !roles.contains("ADMIN")) {
            if (usernameRequest != null && !usernameRequest.equals(usernameSecurity)) {
                throw new ForbiddenException("Forbidden AOP");
            }
        }
    }

    @Before("execution(* root.service.AccountService.updatePassword(*))")
    public void updatePassword(JoinPoint joinPoint) {
        AccountDTO accountDTO = (AccountDTO) joinPoint.getArgs()[0]; // array các tham số
        if (!accountRepo.existsById(accountDTO.getId())) {
            throw new ResourceNotFoundException("account with id [" + accountDTO.getId() + "] not found");
        }
        String usernameRequest = accountRepo.findById(accountDTO.getId()).get().getUsername();
        String usernameSecurity = getUsernameSecurityContextHolder();

        List<String> roles = getRolesSecurityContextHolder();
        // Chỉ username nào sở hữu account.id đó và role ADMIN, còn lại bị cấm
        if (roles == null || !roles.contains("ADMIN")) {
            if (usernameRequest != null && !usernameRequest.equals(usernameSecurity)) {
                throw new ForbiddenException("Forbidden ADMIN AOP");
            }
        }
    }

    @Before("execution(* root.service.AccountService.delete(*))")
    public void delete(JoinPoint joinPoint) {
        long id = (long) joinPoint.getArgs()[0]; // array các tham số
        if (!accountRepo.existsById(id)) {
            throw new ResourceNotFoundException("account with id [" + id + "] not found");
        }
        String usernameRequest = accountRepo.findById(id).get().getUsername();
        String usernameSecurity = getUsernameSecurityContextHolder();

        List<String> roles = getRolesSecurityContextHolder();
        // Chỉ username nào sở hữu account.id đó và role ADMIN, còn lại bị cấm
        if (roles == null || !roles.contains("ADMIN")) {
            if (usernameRequest != null && !usernameRequest.equals(usernameSecurity)) {
                throw new ForbiddenException("Forbidden ADMIN AOP");
            }
        }
    }

    @Before("execution(* root.service.AccountService.getById(*))")
    public void getById(JoinPoint joinPoint) {
        long id = (long) joinPoint.getArgs()[0]; // array các tham số
        if (!accountRepo.existsById(id)) {
            throw new ResourceNotFoundException("account with id [" + id + "] not found");
        }
        String usernameRequest = accountRepo.findById(id).get().getUsername();
        String usernameSecurity = getUsernameSecurityContextHolder();

        List<String> roles = getRolesSecurityContextHolder();
        // Chỉ username nào sở hữu account.id đó và role ADMIN, còn lại bị cấm
        if (roles == null || !roles.contains("ADMIN")) {
            if (usernameRequest != null && !usernameRequest.equals(usernameSecurity)) {
                throw new ForbiddenException("Forbidden ADMIN AOP");
            }
        }
    }

    /**
     * AccountService.searchByName():
     * - Search nên chỉ dành cho role ADMIN (là dễ nhất: dùng Interceptor)
     * - Đúng: khi search với role nào, kết quả chỉ về role đó và role thấp hơn,
     * ko đc trả về role cao hơn
     */
    /*@Before("execution(* root.service.AccountService.searchByName(*))")
    public void searchService(JoinPoint joinPoint) {
        List<String> roles = getRolesSecurityContextHolder();
        if (roles == null || !roles.contains("ADMIN")) {
            throw new ForbiddenException("Forbidden ADMIN AOP");
        }
    }*/

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
