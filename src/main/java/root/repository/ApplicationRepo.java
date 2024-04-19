package root.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import root.entity.Application;
import root.entity.UserContact;
import root.enums.ApplicationStatusEnum;

import java.util.Optional;

public interface ApplicationRepo extends JpaRepository<Application, Long> {
    String C1 = "a.resume.id = :c1 ";
    String C2 = "a.job.id = :c2 ";
    String QUERY_BEGIN = "SELECT a FROM Application a WHERE ";

    @Query(QUERY_BEGIN + C1)
    Page<Application> searchByC1( Long c1, Pageable pageable);

    @Query(QUERY_BEGIN + C2)
    Page<Application> searchByC2(Long c2, Pageable pageable);

    @Query(QUERY_BEGIN + C1 + "AND " + C2)
    Page<Application> searchByC1AndC2(Long c1, Long c2, Pageable pageable);

    @Query(
        "SELECT a " +
            "FROM Application a " +
            "WHERE a.resume.id = :s1 AND a.job.company.account.id = :s2"
    )
    Optional<Application> findApplicationByResumeIdAndCompanyId(
        @Param("s1") Long s1,
        @Param("s2") Long s2
    );

    @Query(
        "SELECT uc " +
            "FROM Application a " +
            "JOIN UserContact uc ON a.resume.user.accountId = uc.user.accountId " +
            "WHERE a.resume.id = :s1"
    )
    Optional<UserContact> findUserContactByResumeId(@Param("s1") Long s1);
}
