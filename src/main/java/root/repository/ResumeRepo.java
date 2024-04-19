package root.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import root.entity.LocationDistrict;
import root.entity.Resume;
import root.enums.JobLevelEnum;

import java.util.Optional;

public interface ResumeRepo extends JpaRepository<Resume, Long> {
    String C1 = "r.workingPreference.locationCity.id = :c1 ";
    String C2 = "r.workingPreference.jobFunction.id = :c2 ";
    String C3 = "ul.language.id = :c3 ";
    String C4 = "r.workingPreference.jobLevel = :c4 ";
    String C5 = "r.userProfile.yearsExperience >= :c5 ";
    String C6 = "r.userProfile.yearsExperience <= :c6 ";
    String C7 = "r.workingPreference.salary >= :c7 ";
    String C8 = "r.workingPreference.salary <= :c8 ";
    String QUERY_BEGIN = "SELECT r FROM Resume r " +
        "JOIN UserSkill us ON r.id = us.resume.id " +
        "JOIN UserLanguage ul ON r.id = ul.resume.id " +
        "WHERE r.resumeStatus.isSearch = true AND ";
    String QUERY_END = C5 + "AND " +  C6 + "AND " +  C7 + "AND " +  C8 + "AND "
        + "(r.workingPreference.jobTitle LIKE :s OR us.skill.name LIKE :s)";

    @Query(QUERY_BEGIN + QUERY_END)
    Page<Resume> searchBy( String s,
        Integer c5, Integer c6, Double c7, Double c8, Pageable pageable);

    @Query(QUERY_BEGIN +
        C1 + "AND " +
        QUERY_END)
    Page<Resume> searchByC1(String s,
        Integer c1,
        Integer c5, Integer c6, Double c7, Double c8, Pageable pageable);

    @Query(QUERY_BEGIN +
        C2 + "AND " +
        QUERY_END)
    Page<Resume> searchByC2(String s,
        Integer c2,
        Integer c5, Integer c6, Double c7, Double c8, Pageable pageable);

    @Query(QUERY_BEGIN +
        C3 + "AND " +
        QUERY_END)
    Page<Resume> searchByC3(String s,
        Integer c3,
        Integer c5, Integer c6, Double c7, Double c8, Pageable pageable);

    @Query(QUERY_BEGIN +
        C4 + "AND " +
        QUERY_END)
    Page<Resume> searchByC4(String s,
        JobLevelEnum c4,
        Integer c5, Integer c6, Double c7, Double c8, Pageable pageable);

    @Query(QUERY_BEGIN +
        C1 + "AND " +
        C2 + "AND " +
        QUERY_END)
    Page<Resume> searchByC1AndC2(String s,
        Integer c1,
        Integer c2,
        Integer c5, Integer c6, Double c7, Double c8, Pageable pageable);

    @Query(QUERY_BEGIN +
        C1 + "AND " +
        C3 + "AND " +
        QUERY_END)
    Page<Resume> searchByC1AndC3(String s,
        Integer c1,
        Integer c3,
        Integer c5, Integer c6, Double c7, Double c8, Pageable pageable);

    @Query(QUERY_BEGIN +
        C1 + "AND " +
        C4 + "AND " +
        QUERY_END)
    Page<Resume> searchByC1AndC4(String s,
        Integer c1,
        JobLevelEnum c4,
        Integer c5, Integer c6, Double c7, Double c8, Pageable pageable);

    @Query(QUERY_BEGIN +
        C2 + "AND " +
        C3 + "AND " +
        QUERY_END)
    Page<Resume> searchByC2AndC3(String s,
        Integer c2,
        Integer c3,
        Integer c5, Integer c6, Double c7, Double c8, Pageable pageable);

    @Query(QUERY_BEGIN +
        C2 + "AND " +
        C4 + "AND " +
        QUERY_END)
    Page<Resume> searchByC2AndC4(String s,
        Integer c2,
        JobLevelEnum c4,
        Integer c5, Integer c6, Double c7, Double c8, Pageable pageable);

    @Query(QUERY_BEGIN +
        C3 + "AND " +
        C4 + "AND " +
        QUERY_END)
    Page<Resume> searchByC3AndC4(String s,
        Integer c3,
        JobLevelEnum c4,
        Integer c5, Integer c6, Double c7, Double c8, Pageable pageable);

    @Query(QUERY_BEGIN +
        C1 + "AND " +
        C2 + "AND " +
        C3 + "AND " +
        QUERY_END)
    Page<Resume> searchByC1AndC2AndC3(String s,
        Integer c1,
        Integer c2,
        Integer c3,
        Integer c5, Integer c6, Double c7, Double c8, Pageable pageable);

    @Query(QUERY_BEGIN +
        C1 + "AND " +
        C2 + "AND " +
        C4 + "AND " +
        QUERY_END)
    Page<Resume> searchByC1AndC2AndC4(String s,
        Integer c1,
        Integer c2,
        JobLevelEnum c4,
        Integer c5, Integer c6, Double c7, Double c8, Pageable pageable);

    @Query(QUERY_BEGIN +
        C1 + "AND " +
        C3 + "AND " +
        C4 + "AND " +
        QUERY_END)
    Page<Resume> searchByC1AndC3AndC4(String s,
        Integer c1,
        Integer c3,
        JobLevelEnum c4,
        Integer c5, Integer c6, Double c7, Double c8, Pageable pageable);

    @Query(QUERY_BEGIN +
        C2 + "AND " +
        C3 + "AND " +
        C4 + "AND " +
        QUERY_END)
    Page<Resume> searchByC2AndC3AndC4(String s,
        Integer c2,
        Integer c3,
        JobLevelEnum c4,
        Integer c5, Integer c6, Double c7, Double c8, Pageable pageable);

    @Query(QUERY_BEGIN +
        C1 + "AND " +
        C2 + "AND " +
        C3 + "AND " +
        C4 + "AND " +
        QUERY_END)
    Page<Resume> searchByC1AndC2AndC3AndC4(String s,
        Integer c1,
        Integer c2,
        Integer c3,
        JobLevelEnum c4,
        Integer c5, Integer c6, Double c7, Double c8, Pageable pageable);
}