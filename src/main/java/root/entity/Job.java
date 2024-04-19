package root.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Job extends TimeAuditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Company company;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private JobStatus jobStatus;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private JobSummary jobSummary;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private JobDetail jobDetail;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<JobSkill> jobSkills;
}
