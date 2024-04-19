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
public class UserSummaryDTO {
    private Long id;
    private String summary; // Mục tiêu nghề nghiệp

    @JsonBackReference
    private ResumeDTO resume;
}
