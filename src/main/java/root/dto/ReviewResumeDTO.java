package root.dto;

import lombok.*;
import root.enums.EmployeeStatusEnum;

@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReviewResumeDTO extends TimeAuditableDTO {
	private Long id;
    private EmployeeStatusEnum employeeStatus;
	private ResumeDTO resume;
	private JobDTO job;
}
