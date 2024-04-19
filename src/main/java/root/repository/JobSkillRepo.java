package root.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import root.entity.JobSkill;

public interface JobSkillRepo extends JpaRepository<JobSkill, Long> {
}
