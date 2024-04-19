package root.client.controller;//package root.client.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import root.client.service.ProvinceFeignService;
import root.dto.ResponseDTO;

@RequiredArgsConstructor
@RestController
@RequestMapping("/province-feign")
public class ProvinceFeignController {

    private final ProvinceFeignService provinceFeignService;

    @GetMapping
    public ResponseDTO<Void> createAll() {
        provinceFeignService.createAllProvincesAndDistricts();
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }
}
