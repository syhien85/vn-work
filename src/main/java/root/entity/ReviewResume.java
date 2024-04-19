package root.entity;

import jakarta.persistence.*;
import lombok.*;
import root.enums.EmployeeStatusEnum;

@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(
	uniqueConstraints = {
		@UniqueConstraint(
			name = "UniqueResumeIdAndJobId",
			columnNames = {"resume_id", "job_id"}
		)
	}
)
public class ReviewResume extends TimeAuditable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @Enumerated(EnumType.ORDINAL)
    private EmployeeStatusEnum employeeStatus;

	@OneToOne
	private Resume resume;

	@OneToOne
	private Job job;
}
