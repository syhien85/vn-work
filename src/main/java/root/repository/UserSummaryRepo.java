package root.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import root.entity.UserSummary;

public interface UserSummaryRepo extends JpaRepository<UserSummary, Long> {
}
