package root.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import root.entity.WorkingPreference;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class JobFunctionDTO {
    private Integer id;
    private String name; // ngành nghề (item)

    private JobFunctionParentDTO jobFunctionParent;
}
