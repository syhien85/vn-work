package root.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import root.entity.UserAttachedCV;
import root.entity.UserSummary;

public interface UserAttachedCVRepo extends JpaRepository<UserAttachedCV, Long> {
}
