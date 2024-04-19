package root.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import root.entity.Language;

public interface LanguageRepo extends JpaRepository<Language, Integer> {
    boolean existsByName(String name);

    @Query("SELECT l FROM Language l WHERE l.name LIKE :s")
    Page<Language> searchByName(@Param("s") String s, Pageable pageable);
}
