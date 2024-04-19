package root.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class JobDetailDTO {
    private Long id;

    private String jobTitle;
    private String jobDescription;
    private String jobRequirement;
    private String benefits;

    private LocationDTO workingLocation;
}
