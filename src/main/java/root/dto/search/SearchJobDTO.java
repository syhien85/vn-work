package root.dto.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import root.dto.SearchDTO;
import root.enums.JobLevelEnum;
import root.enums.TypeWorkingEnum;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class SearchJobDTO extends SearchDTO {
    private Integer locationCityId; // city
    private Integer jobFunctionId; // lĩnh vực
//    private Integer languageId; // Ngôn ngữ
    private JobLevelEnum jobLevel; // cấp bậc
    private TypeWorkingEnum typeWorking; // cấp bậc
    private Integer yearsExperienceMin; // số năm kinh nghiệm
    private Integer yearsExperienceMax; // số năm kinh nghiệm
    private Double salaryMin; // lương
    private Double salaryMax; // lương

    public SearchJobDTO() {
        locationCityId = null;
        jobFunctionId = null;
//        languageId = null;
        jobLevel = null;
        typeWorking = null;
        yearsExperienceMin = null;
        yearsExperienceMax = null;
        salaryMin = null;
        salaryMax = null;
    }
}
