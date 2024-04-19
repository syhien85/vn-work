package root.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
/*@Table(
    uniqueConstraints = {
        @UniqueConstraint(
            name = "UniqueSkillIdAndJobId",
            columnNames = {"skill_id", "job_id"}
        )
    }
)*/
public class JobSkill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Skill skill;

    @ManyToOne
    private Job job;
}
