package root.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LocationDistrictDTO {
    private Integer id;
    private String name;

    private LocationCityDTO locationCity;
}
