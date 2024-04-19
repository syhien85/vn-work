package root.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import root.enums.JobLevelEnum;
import root.enums.TypeWorkingEnum;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class JobSummaryDTO {
    private Long id;

    private LocationCityDTO locationCity;

    private Double salaryMin;
    private Double salaryMax;
    private String salary;
    private String prettySalary;
    private Boolean isSalaryVisible;

    private JobLevelEnum jobLevel;
    private TypeWorkingEnum typeWorking;
    private Integer yearsExperience;

//    private List<SkillDTO> skills;
    private LanguageDTO language; // languageSelectedId
    private JobFunctionDTO jobFunction;
    private List<IndustryDTO> industries;
}
