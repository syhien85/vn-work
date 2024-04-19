package root.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Resume extends TimeAuditable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

//	private String type; // mẫu trình bày cv
//	private String cvLevel; // level cv

	@OneToOne(fetch = FetchType.EAGER)
	private User user;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private ResumeStatus resumeStatus;

	@OneToOne(mappedBy = "resume", fetch = FetchType.EAGER)
	private UserProfile userProfile;

	@OneToOne(mappedBy = "resume", fetch = FetchType.EAGER)
	private WorkingPreference workingPreference;

	@OneToOne(mappedBy = "resume", fetch = FetchType.EAGER)
	private UserSummary userSummary;

	@OneToMany(mappedBy = "resume", fetch = FetchType.EAGER)
	private List<UserExperience> userExperiences;

	@OneToMany(mappedBy = "resume", fetch = FetchType.EAGER)
	private List<UserEducation> userEducations;

	@OneToMany(mappedBy = "resume", fetch = FetchType.EAGER)
	private List<UserSkill> userSkills;

	@OneToMany(mappedBy = "resume", fetch = FetchType.EAGER)
	private List<UserLanguage> userLanguages;

	@OneToOne(mappedBy = "resume", fetch = FetchType.EAGER)
	private UserAttachedCV userAttachedCV;
}
