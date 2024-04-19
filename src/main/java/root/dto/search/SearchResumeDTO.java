package root.dto.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import root.dto.SearchDTO;
import root.enums.JobLevelEnum;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class SearchResumeDTO extends SearchDTO {
//    private String jobTitle; // vị trí (key)
//    private String skill; // kỹ năng (key)
    private Integer locationCityId; // city
    private Integer jobFunctionId; // lĩnh vực
    private Integer languageId; // Ngôn ngữ
    private JobLevelEnum jobLevel; // cấp bậc
    private Integer yearsExperienceMin; // số năm kinh nghiệm
    private Integer yearsExperienceMax; // số năm kinh nghiệm
    private Double salaryMin; // lương
    private Double salaryMax; // lương

    public SearchResumeDTO() {
        locationCityId = null;
        jobFunctionId = null;
        languageId = null;
        jobLevel = null;
        yearsExperienceMin = null;
        yearsExperienceMax = null;
        salaryMin = null;
        salaryMax = null;
    }
}
