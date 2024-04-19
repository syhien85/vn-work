package root.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import root.dto.LocationCityDTO;
import root.enums.JobLevelEnum;
import root.enums.TypeWorkingEnum;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class JobSummary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private LocationCity locationCity; // địa điểm làm việc (thành phố)

    private Double salaryMin;
    private Double salaryMax;
    private String salary;
    private String prettySalary;
    private Boolean isSalaryVisible;

    @Enumerated(EnumType.ORDINAL)
    private JobLevelEnum jobLevel;

    @Enumerated(EnumType.ORDINAL)
    private TypeWorkingEnum typeWorking;

    private Integer yearsExperience;

//    @ManyToMany(fetch = FetchType.EAGER)
//    private List<Skill> skills;

    @ManyToOne
    private Language language; // languageSelectedId

    @ManyToOne
    private JobFunction jobFunction;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Industry> industries; // lĩnh vực (CNTT)
}
