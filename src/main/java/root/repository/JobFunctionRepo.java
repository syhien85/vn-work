package root.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import root.entity.JobFunction;
import root.entity.LocationDistrict;

public interface JobFunctionRepo extends JpaRepository<JobFunction, Integer> {
    boolean existsByName(String name);

    /*@Query("SELECT j FROM JobFunction j WHERE j.name LIKE :s")
    Page<JobFunction> searchByName(@Param("s") String s, Pageable pageable);*/

    String C1 = "j.jobFunctionParent.id = :c1 ";
    String queryKeyword = "(j.name LIKE :s)";

    @Query("SELECT j FROM JobFunction j WHERE " + queryKeyword)
    Page<JobFunction> searchBy(
        @Param("s") String s,
        Pageable pageable
    );

    @Query("SELECT j FROM JobFunction j WHERE " + C1 + "AND " + queryKeyword)
    Page<JobFunction> searchByC1(
        @Param("s") String s,
        @Param("c1") Integer c1,
        Pageable pageable
    );
}
