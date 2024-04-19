package root.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import root.enums.HighestEducationEnum;
import root.enums.JobLevelEnum;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class UserProfile {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String fullName;
	private String firstName;
	private String lastName;
	private String avatarUrl;

	private String jobTitle; // Chức danh
	private Integer yearsExperience; // Số năm kinh nghiệm
	@Enumerated(EnumType.ORDINAL)
	private HighestEducationEnum highestDegree; // bằng cấp cao nhất
	private Double currentSalary; // Mức lương hiện tại
	@Enumerated(EnumType.ORDINAL)
	private JobLevelEnum jobLevel; // Cấp bậc hiện tại (nhân viên, leader,..)

	@ManyToOne
	private JobFunction jobFunction;

	@ManyToMany(fetch = FetchType.EAGER)
	private List<Industry> industries; // Lĩnh vực (CNTT)

	@OneToOne
	private Resume resume;
}
