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
@Table(
    uniqueConstraints = {
        @UniqueConstraint(
            name = "UniqueSkillIdAndUserId",
            columnNames = {"skill_id", "resume_id"}
        )
    }
)
public class UserSkill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Skill skill;

    private Integer level;

    @ManyToOne
    private Resume resume;
}
