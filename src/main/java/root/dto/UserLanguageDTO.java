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
public class UserLanguageDTO {
    private Long id;
    private LanguageDTO language;
    private Integer level;
    @JsonBackReference
    private ResumeDTO resume;
}
