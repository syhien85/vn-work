package root.dto.search;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import root.dto.SearchDTO;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class SearchLocationDistrictDTO extends SearchDTO {
    private Integer locationCityId;

    public SearchLocationDistrictDTO() {
        locationCityId = null;
    }
}
