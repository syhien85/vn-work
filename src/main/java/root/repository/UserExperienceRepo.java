package root.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import root.entity.UserExperience;

public interface UserExperienceRepo extends JpaRepository<UserExperience, Long> {
}
