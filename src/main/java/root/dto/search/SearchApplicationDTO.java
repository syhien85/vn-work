package root.dto.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import root.dto.SearchDTO;
import root.enums.ApplicationStatusEnum;
import root.enums.JobLevelEnum;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class SearchApplicationDTO extends SearchDTO {
    private Long resumeId;
    private Long jobId;

    public SearchApplicationDTO() {
        resumeId = null;
        jobId = null;
    }
}
