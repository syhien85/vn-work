package root.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import root.entity.LocationCity;

public interface LocationCityRepo extends JpaRepository<LocationCity, Integer> {
    boolean existsByName(String name);

    @Query("SELECT l FROM LocationCity l WHERE l.name LIKE :s")
    Page<LocationCity> searchByName(@Param("s") String s, Pageable pageable);
}
