package root.dto.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import root.dto.SearchDTO;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class SearchJobByCompanyDTO extends SearchDTO {
    private Long companyId;
    private Integer locationCityId; // city

    public SearchJobByCompanyDTO() {
        companyId = null;
        locationCityId = null;
    }
}
