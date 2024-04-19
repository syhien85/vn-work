package root.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import root.entity.Industry;

public interface IndustryRepo extends JpaRepository<Industry, Long> {
    @Query("SELECT i FROM Industry i WHERE i.name LIKE :s")
    Page<Industry> searchByName(@Param("s") String s, Pageable pageable);
}
