package root.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import root.entity.JobFunctionParent;

public interface JobFunctionParentRepo extends JpaRepository<JobFunctionParent, Integer> {
    @Query("SELECT i FROM JobFunctionParent i WHERE i.name LIKE :s")
    Page<JobFunctionParent> searchByName(@Param("s") String s, Pageable pageable);

    boolean existsByName(String name);
}
