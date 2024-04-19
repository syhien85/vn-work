package root.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import root.dto.LocationCityDTO;
import root.dto.PageDTO;
import root.dto.ResponseDTO;
import root.dto.SearchDTO;
import root.service.LocationCityService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/location-city")
public class LocationCityController {

    private final LocationCityService locationCityService;

    @PostMapping
    public ResponseDTO<Void> create(@RequestBody @Valid LocationCityDTO locationCityDTO) {
        locationCityService.create(locationCityDTO);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @PutMapping
    public ResponseDTO<Void> update(@RequestBody LocationCityDTO locationCityDTO) {
        locationCityService.update(locationCityDTO);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @DeleteMapping
    public ResponseDTO<Void> delete(@RequestParam("id") Integer id) {
        locationCityService.delete(id);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @GetMapping
    public ResponseDTO<LocationCityDTO> getById(@RequestParam("id") Integer id) {
        return ResponseDTO.<LocationCityDTO>builder()
            .status(200).msg("OK")
            .data(locationCityService.getById(id))
            .build();
    }

    @PostMapping("/search")
    public ResponseDTO<PageDTO<LocationCityDTO>> search(@RequestBody SearchDTO searchDTO) {
        return ResponseDTO.<PageDTO<LocationCityDTO>>builder().status(200).msg("OK")
            .data(locationCityService.searchService(searchDTO))
            .build();
    }
}
