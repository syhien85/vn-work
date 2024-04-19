package root.dto.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import root.dto.SearchDTO;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class SearchJobFunctionDTO extends SearchDTO {
    private Integer jobFunctionParentId;

    public SearchJobFunctionDTO() {
        jobFunctionParentId = null;
    }
}
