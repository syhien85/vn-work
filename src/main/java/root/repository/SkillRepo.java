package root.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import root.entity.Language;
import root.entity.Skill;

import java.util.Optional;

public interface SkillRepo extends JpaRepository<Skill, Long> {
    boolean existsByName(String name);

    Optional<Skill> findByName(String name);

    @Query("SELECT s FROM Skill s WHERE s.name LIKE :s")
    Page<Skill> searchByName(@Param("s") String s, Pageable pageable);
}
