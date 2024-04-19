package root.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import root.entity.LocationCity;
import root.enums.JobLevelEnum;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class WorkingPreferenceDTO { // Công việc mong muốn
    private Long id;
    private String jobTitle; // Chức danh
    private JobLevelEnum jobLevel; // cấp bậc (nhân viên, leader, ..)
    private String benefits; // phúc lợi
    private Double salary; // mức lương mong muốn
    private Boolean isRelocate; // có thể thay đổi nơi làm việc

    private LocationCityDTO locationCity; // nơi làm việc (tỉnh, thành phố)

    private LanguageDTO language; //

    private JobFunctionDTO jobFunction;

    private List<IndustryDTO> industries;

    @JsonBackReference
    private ResumeDTO resume;
}
