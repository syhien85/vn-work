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
            name = "UniqueLanguageIdAndUserId",
            columnNames = {"language_id", "resume_id"}
        )
    }
)
public class UserLanguage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Language language;

    private Integer level;

    @ManyToOne
    private Resume resume;
}
