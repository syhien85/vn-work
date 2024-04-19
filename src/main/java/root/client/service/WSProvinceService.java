package root.client.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import root.client.dto.RDistrict;
import root.client.dto.RProvince;

@FeignClient(
    value = "provinceService",
    url = "https://vapi.vnappmob.com/api/province"
)
public interface WSProvinceService {

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    RProvince getAllProvince();

    @GetMapping("/district/{id}")
    @ResponseStatus(HttpStatus.OK)
    RDistrict getDistrictByProvinceId(@PathVariable String id);
}