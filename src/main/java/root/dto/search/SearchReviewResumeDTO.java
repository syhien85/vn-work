package root.dto.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import root.dto.SearchDTO;
import root.enums.EmployeeStatusEnum;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class SearchReviewResumeDTO extends SearchDTO {
    private Long resumeId;
    private Long jobId;
    private EmployeeStatusEnum employeeStatus;

    public SearchReviewResumeDTO() {
        resumeId = null;
        jobId = null;
        employeeStatus = null;
    }
}
