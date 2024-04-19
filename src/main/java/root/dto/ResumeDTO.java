package root.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import java.util.List;

@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResumeDTO extends TimeAuditableDTO {
	private Long id;
	private UserDTO user;

	private ResumeStatusDTO resumeStatus;

	@JsonManagedReference
    private UserProfileDTO userProfile;
    @JsonManagedReference
    private WorkingPreferenceDTO workingPreference;
    @JsonManagedReference
    private UserSummaryDTO userSummary;
    @JsonManagedReference
    private List<UserExperienceDTO> userExperiences;
    @JsonManagedReference
    private List<UserEducationDTO> userEducations;
    @JsonManagedReference
    private List<UserSkillDTO> userSkills;
    @JsonManagedReference
    private List<UserLanguageDTO> userLanguages;
    @JsonManagedReference
    private UserAttachedCVDTO userAttachedCV;
}
