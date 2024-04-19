package root.dto;

import lombok.*;
import root.enums.ApplicationStatusEnum;
import root.enums.EmployeeStatusEnum;

@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ApplicationDTO extends TimeAuditableDTO {
	private Long id;

	private ApplicationStatusEnum applicationStatus;
	private EmployeeStatusEnum employeeStatus;

	private ResumeDTO resume;
	private JobDTO job;
}
