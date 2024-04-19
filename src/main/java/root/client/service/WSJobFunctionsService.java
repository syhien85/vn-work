package root.client.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import root.client.dto.RJobFunction;

@FeignClient(
    value = "jobFunctionsService",
    url = "https://ms.vietnamworks.com/meta/v1.2/job-functions"
)
public interface WSJobFunctionsService {

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    RJobFunction getAll();
}