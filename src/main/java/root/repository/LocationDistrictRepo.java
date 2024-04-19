package root.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import root.entity.LocationDistrict;

public interface LocationDistrictRepo extends JpaRepository<LocationDistrict, Integer> {
    boolean existsByName(String name);

    /*@Query("SELECT l FROM LocationDistrict l WHERE l.name LIKE :s")
    Page<LocationDistrict> searchByName(@Param("s") String s, Pageable pageable);*/

    String C1 = "l.locationCity.id = :c1 ";
    String queryKeyword = "(l.name LIKE :s)";

    @Query("SELECT l FROM LocationDistrict l WHERE " + queryKeyword)
    Page<LocationDistrict> searchBy(
        @Param("s") String s,
        Pageable pageable
    );

    @Query("SELECT l FROM LocationDistrict l WHERE " + C1 + "AND " + queryKeyword)
    Page<LocationDistrict> searchByC1(
        @Param("s") String s,
        @Param("c1") Integer c1,
        Pageable pageable
    );
}
