package root.client.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import root.client.service.JobFunctionFeignService;
import root.dto.ResponseDTO;
import root.entity.JobFunctionParent;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/job-function-feign")
public class JobFunctionFeignController {

    private final JobFunctionFeignService jobFunctionFeignService;

    @GetMapping
    public ResponseDTO<Void> createAll() {
        jobFunctionFeignService.createAllJobFunctions();
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }
}
