package root.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import root.entity.Job;
import root.enums.JobLevelEnum;
import root.enums.TypeWorkingEnum;

public interface JobRepo extends JpaRepository<Job, Long> {
    String C1 = "j.jobSummary.locationCity.id = :c1 ";
    String C2 = "j.jobSummary.jobFunction.id = :c2 ";
    String C3 = "j.jobSummary.jobLevel = :c3 ";
    String C4 = "j.jobSummary.typeWorking = :c4 ";
    String C5 = "j.jobSummary.yearsExperience >= :c5 ";
    String C6 = "j.jobSummary.yearsExperience <= :c6 ";
    String C7 = "j.jobSummary.salaryMin >= :c7 ";
    String C8 = "j.jobSummary.salaryMax <= :c8 ";
    String QUERY_BEGIN = "SELECT j FROM Job j JOIN JobSkill js ON j.id = js.job.id " +
        "WHERE j.jobStatus.isActive = true AND ";
    String QUERY_END = C5 + "AND " +  C6 + "AND " +  C7 + "AND " +  C8 + "AND "
        + "(j.jobDetail.jobTitle LIKE :s OR js.skill.name LIKE :s OR j.company.companyInfo.companyName LIKE :s)";

    @Query(QUERY_BEGIN + QUERY_END)
    Page<Job> searchBy(String s,
         Integer c5, Integer c6, Double c7, Double c8, Pageable pageable);

//    @Query("SELECT j FROM Job j WHERE j.jobStatus.isActive = true AND j.jobSummary.locationCity.id = :c1")
//    Page<Job> searchByC1(Integer c1, Pageable pageable);

    @Query(QUERY_BEGIN +
        C1 + "AND " +
        QUERY_END)
    Page<Job> searchByC1(String s,
        Integer c1,
        Integer c5, Integer c6, Double c7, Double c8, Pageable pageable);

    @Query(QUERY_BEGIN +
        C2 + "AND " +
        QUERY_END)
    Page<Job> searchByC2(String s,
        Integer c2,
        Integer c5, Integer c6, Double c7, Double c8, Pageable pageable);

    @Query(QUERY_BEGIN +
        C3 + "AND " +
        QUERY_END)
    Page<Job> searchByC3(String s,
        JobLevelEnum c3,
        Integer c5, Integer c6, Double c7, Double c8, Pageable pageable);

    @Query(QUERY_BEGIN +
        C4 + "AND " +
        QUERY_END)
    Page<Job> searchByC4(String s,
        TypeWorkingEnum c4,
        Integer c5, Integer c6, Double c7, Double c8, Pageable pageable);

    @Query(QUERY_BEGIN +
        C1 + "AND " +
        C2 + "AND " +
        QUERY_END)
    Page<Job> searchByC1AndC2(String s,
        Integer c1,
        Integer c2,
        Integer c5, Integer c6, Double c7, Double c8, Pageable pageable);

    @Query(QUERY_BEGIN +
        C1 + "AND " +
        C3 + "AND " +
        QUERY_END)
    Page<Job> searchByC1AndC3(String s,
        Integer c1,
        JobLevelEnum c3,
        Integer c5, Integer c6, Double c7, Double c8, Pageable pageable);

    @Query(QUERY_BEGIN +
        C1 + "AND " +
        C4 + "AND " +
        QUERY_END)
    Page<Job> searchByC1AndC4(String s,
        Integer c1,
        TypeWorkingEnum c4,
        Integer c5, Integer c6, Double c7, Double c8, Pageable pageable);

    @Query(QUERY_BEGIN +
        C2 + "AND " +
        C3 + "AND " +
        QUERY_END)
    Page<Job> searchByC2AndC3(String s,
        Integer c2,
        JobLevelEnum c3,
        Integer c5, Integer c6, Double c7, Double c8, Pageable pageable);

    @Query(QUERY_BEGIN +
        C2 + "AND " +
        C4 + "AND " +
        QUERY_END)
    Page<Job> searchByC2AndC4(String s,
        Integer c2,
        TypeWorkingEnum c4,
        Integer c5, Integer c6, Double c7, Double c8, Pageable pageable);

    @Query(QUERY_BEGIN +
        C3 + "AND " +
        C4 + "AND " +
        QUERY_END)
    Page<Job> searchByC3AndC4(String s,
        JobLevelEnum c3,
        TypeWorkingEnum c4,
        Integer c5, Integer c6, Double c7, Double c8, Pageable pageable);

    @Query(QUERY_BEGIN +
        C1 + "AND " +
        C2 + "AND " +
        C3 + "AND " +
        QUERY_END)
    Page<Job> searchByC1AndC2AndC3(String s,
        Integer c1,
        Integer c2,
        JobLevelEnum c3,
        Integer c5, Integer c6, Double c7, Double c8, Pageable pageable);

    @Query(QUERY_BEGIN +
        C1 + "AND " +
        C2 + "AND " +
        C4 + "AND " +
        QUERY_END)
    Page<Job> searchByC1AndC2AndC4(String s,
        Integer c1,
        Integer c2,
        TypeWorkingEnum c4,
        Integer c5, Integer c6, Double c7, Double c8, Pageable pageable);

    @Query(QUERY_BEGIN +
        C1 + "AND " +
        C3 + "AND " +
        C4 + "AND " +
        QUERY_END)
    Page<Job> searchByC1AndC3AndC4(String s,
        Integer c1,
        JobLevelEnum c3,
        TypeWorkingEnum c4,
        Integer c5, Integer c6, Double c7, Double c8, Pageable pageable);

    @Query(QUERY_BEGIN +
        C2 + "AND " +
        C3 + "AND " +
        C4 + "AND " +
        QUERY_END)
    Page<Job> searchByC2AndC3AndC4(String s,
        Integer c2,
        JobLevelEnum c3,
        TypeWorkingEnum c4,
        Integer c5, Integer c6, Double c7, Double c8, Pageable pageable);

    @Query(QUERY_BEGIN +
        C1 + "AND " +
        C2 + "AND " +
        C3 + "AND " +
        C4 + "AND " +
        QUERY_END)
    Page<Job> searchByC1AndC2AndC3AndC4(String s,
        Integer c1,
        Integer c2,
        JobLevelEnum c3,
        TypeWorkingEnum c4,
        Integer c5, Integer c6, Double c7, Double c8, Pageable pageable);


    String D1 = "j.company.account.id = :d1 ";
    String D2 = "j.jobSummary.locationCity.id = :d2 ";
    String QUERY_BEGIN2 = "SELECT j FROM Job j " +
        "WHERE j.jobStatus.isActive = true AND ";

    @Query(QUERY_BEGIN2 +
        D1)
//    @Query("SELECT j FROM Job j WHERE j.company.account.id")
    Page<Job> searchByD1(Long d1, Pageable pageable);

    @Query(QUERY_BEGIN2 +
        D2)
    Page<Job> searchByD2(Integer d2, Pageable pageable);

    @Query(QUERY_BEGIN2 +
        D1 + "AND " +
        D2)
    Page<Job> searchByD1AndD2(Long d1, Integer d2, Pageable pageable);
}
