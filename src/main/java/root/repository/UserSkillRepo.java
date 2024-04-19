package root.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import root.entity.UserSkill;

public interface UserSkillRepo extends JpaRepository<UserSkill, Long> {
}
