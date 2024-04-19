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
public class JobSkillDTO {
    private Long id;
    private SkillDTO skill;

    @JsonBackReference
    private JobDTO job;
}
