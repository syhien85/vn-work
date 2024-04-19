package root.client.dto;

import java.util.List;

public record RDistrict(
    List<DistrictItem> results
) {
    public record DistrictItem(
        String district_id,
        String district_name,
        String province_id
    ) {
    }
}
