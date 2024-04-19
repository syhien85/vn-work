package root.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import root.entity.UserEducation;

public interface UserEducationRepo extends JpaRepository<UserEducation, Long> {
}
