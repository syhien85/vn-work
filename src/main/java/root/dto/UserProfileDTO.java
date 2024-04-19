package root.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import root.enums.HighestEducationEnum;
import root.enums.JobLevelEnum;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserProfileDTO {
	private Long id;
	private String fullName;
	private String firstName;
	private String lastName;
	private String avatarUrl;
	@JsonIgnore
	private MultipartFile file;

	private String jobTitle; // Chức danh
	private Integer yearsExperience; // Số năm kinh nghiệm
	private HighestEducationEnum highestDegree; // bằng cấp cao nhất
	private Double currentSalary; // Mức lương hiện tại
	private JobLevelEnum jobLevel; // cấp bậc hiện tại

	private JobFunctionDTO jobFunction;

	private List<IndustryDTO> industries;

	@JsonBackReference
	private ResumeDTO resume;
}
