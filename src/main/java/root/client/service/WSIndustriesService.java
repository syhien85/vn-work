package root.client.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import root.client.dto.RIndustry;

@FeignClient(
    value = "industriesService",
    url = "https://ms.vietnamworks.com/meta/v1.0/company-industries"
)
public interface WSIndustriesService {

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    RIndustry getAll();
}