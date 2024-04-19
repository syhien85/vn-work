package root.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import root.entity.Company;

public interface CompanyRepo extends JpaRepository<Company, Long> {
    @Query("SELECT c FROM Company c WHERE c.companyInfo.companyName LIKE :s")
    Page<Company> searchByCompanyName(@Param("s") String s, Pageable pageable);
}
