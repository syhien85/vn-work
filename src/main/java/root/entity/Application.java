package root.entity;

import jakarta.persistence.*;
import lombok.*;
import root.enums.ApplicationStatusEnum;
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
public class Application extends TimeAuditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.ORDINAL)
    private ApplicationStatusEnum applicationStatus; // status của người xin việc: APPLIED
    @Enumerated(EnumType.ORDINAL)
    private EmployeeStatusEnum employeeStatus; // status của nhà tuyển dụng: SENT, ACCEPT, DECLINED

    @ManyToOne
    private Resume resume;

    @ManyToOne
    private Job job;
}
