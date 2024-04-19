package root.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class JobDTO extends TimeAuditableDTO {
    private Long id;
    private CompanyDTO company;

    private JobStatusDTO jobStatus;
    private JobSummaryDTO jobSummary;
    private JobDetailDTO jobDetail;

    @JsonManagedReference
    private List<JobSkillDTO> jobSkills;
}
