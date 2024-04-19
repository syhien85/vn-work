package root.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserSkillDTO {
    private Long id;
    private SkillDTO skill;
    private Integer level;

    @JsonBackReference
    private ResumeDTO resume;
}
