package root.client.dto;

import java.util.List;

public record RProvince(
    List<ProvinceItem> results
) {
    public record ProvinceItem(
        String province_id,
        String province_name
    ) {
    }
}
