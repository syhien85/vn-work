package root.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import root.dto.LocationDistrictDTO;
import root.dto.PageDTO;
import root.dto.ResponseDTO;
import root.dto.SearchDTO;
import root.dto.search.SearchLocationDistrictDTO;
import root.service.LocationDistrictService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/location-district")
public class LocationDistrictController {

    private final LocationDistrictService locationDistrictService;

    @PostMapping
    public ResponseDTO<Void> create(@RequestBody @Valid LocationDistrictDTO locationDistrictDTO) {
        locationDistrictService.create(locationDistrictDTO);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @PutMapping
    public ResponseDTO<Void> update(@RequestBody LocationDistrictDTO locationDistrictDTO) {
        locationDistrictService.update(locationDistrictDTO);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @DeleteMapping
    public ResponseDTO<Void> delete(@RequestParam("id") Integer id) {
        locationDistrictService.delete(id);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @GetMapping
    public ResponseDTO<LocationDistrictDTO> getById(@RequestParam("id") Integer id) {
        return ResponseDTO.<LocationDistrictDTO>builder()
            .status(200).msg("OK")
            .data(locationDistrictService.getById(id))
            .build();
    }

    @PostMapping("/search")
    public ResponseDTO<PageDTO<LocationDistrictDTO>> search(@RequestBody SearchLocationDistrictDTO searchDTO) {
        return ResponseDTO.<PageDTO<LocationDistrictDTO>>builder().status(200).msg("OK")
            .data(locationDistrictService.searchService(searchDTO))
            .build();
    }
}
