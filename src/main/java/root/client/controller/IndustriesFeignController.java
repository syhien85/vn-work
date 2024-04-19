package root.client.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import root.client.dto.RIndustry;
import root.client.service.IndustryFeignService;
import root.dto.ResponseDTO;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/industry-feign")
public class IndustriesFeignController {

    private final IndustryFeignService industryFeignService;

    @GetMapping
    public ResponseDTO<Void> createAll() {
        industryFeignService.createAllIndustries();
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }
}
