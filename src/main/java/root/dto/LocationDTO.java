package root.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import root.entity.LocationDistrict;
import root.entity.UserProfile;
import root.entity.WorkingPreference;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LocationDTO {
    private Long id;
    private String address;

    private LocationDistrictDTO locationDistrict;
}
