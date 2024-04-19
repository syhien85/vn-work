package root.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import root.entity.UserLanguage;

public interface UserLanguageRepo extends JpaRepository<UserLanguage, Long> {
}
