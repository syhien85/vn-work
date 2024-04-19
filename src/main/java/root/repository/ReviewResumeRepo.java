package root.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import root.entity.ReviewResume;
import root.enums.EmployeeStatusEnum;

public interface ReviewResumeRepo extends JpaRepository<ReviewResume, Long> {
    String C1 = "rr.resume.id = :c1 ";
    String C2 = "rr.job.id = :c2 ";
    String C3 = "rr.employeeStatus = :c3 ";
    String QUERY_BEGIN = "SELECT rr FROM ReviewResume rr WHERE ";
//    String QUERY_END = "(rr.job.jobDetail.jobTitle LIKE :s)";

    @Query("SELECT rr FROM ReviewResume rr")
    Page<ReviewResume> searchBy(Pageable pageable);

    @Query(QUERY_BEGIN +
        C1)
    Page<ReviewResume> searchByC1(
        Long c1,
        Pageable pageable);

    @Query(QUERY_BEGIN +
        C2)
    Page<ReviewResume> searchByC2(
        Long c2,
        Pageable pageable);

    @Query(QUERY_BEGIN +
        C3)
    Page<ReviewResume> searchByC3(
        EmployeeStatusEnum c3,
        Pageable pageable);

    @Query(QUERY_BEGIN +
        C1 + "AND " +
        C2)
    Page<ReviewResume> searchByC1AndC2(
        Long c1,
        Long c2,
        Pageable pageable);

    @Query(QUERY_BEGIN +
        C1 + "AND " +
        C3)
    Page<ReviewResume> searchByC1AndC3(
        Long c1,
        EmployeeStatusEnum c3,
        Pageable pageable);

    @Query(QUERY_BEGIN +
        C2 + "AND " +
        C3)
    Page<ReviewResume> searchByC2AndC3(
        Long c2,
        EmployeeStatusEnum c3,
        Pageable pageable);

    @Query(QUERY_BEGIN +
        C1 + "AND " +
        C2 + "AND " +
        C3)
    Page<ReviewResume> searchByC1AndC2AndC3(
        Long c1,
        Long c2,
        EmployeeStatusEnum c3,
        Pageable pageable);
}
