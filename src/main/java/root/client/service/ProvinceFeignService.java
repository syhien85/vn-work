package root.client.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import root.client.dto.RDistrict;
import root.client.dto.RProvince;
import root.entity.LocationCity;
import root.entity.LocationDistrict;
import root.repository.LocationCityRepo;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProvinceFeignService {

    private final WSProvinceService wsProvinceService;
    private final LocationCityRepo locationCityRepo;

    public void createAllProvincesAndDistricts() {
        List<RProvince.ProvinceItem> provinceItems = wsProvinceService.getAllProvince().results();

        List<LocationCity> locationCities = provinceItems.stream()
            .map(provinceItem ->
                LocationCity
                    .builder()
                    .id(Integer.valueOf(provinceItem.province_id()))
                    .name(provinceItem.province_name())
                    .locationDistricts(getDistrictsByProvinceId(provinceItem.province_id()))
                    .build()
            )
            .toList();
//        System.out.println(locationCities);

        locationCityRepo.saveAll(locationCities);
    }

    private List<LocationDistrict> getDistrictsByProvinceId(String locationCityId) {
        List<RDistrict.DistrictItem> districtByProvinceId = wsProvinceService
            .getDistrictByProvinceId(locationCityId)
            .results();

        return districtByProvinceId.stream()
            .map(districtItem ->
                LocationDistrict
                    .builder()
                    .id(Integer.valueOf(districtItem.district_id()))
                    .name(districtItem.district_name())
                    .locationCity(
                        LocationCity.builder()
                            .id(Integer.valueOf(locationCityId))
                            .build()
                    )
                    .build()
                )
            .toList();
    }
}

