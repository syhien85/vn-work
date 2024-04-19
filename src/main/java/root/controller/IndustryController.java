package root.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import root.dto.*;
import root.service.IndustryService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/industry")
public class IndustryController {

    private final IndustryService industryService;

    @PostMapping
    public ResponseDTO<Void> create(@RequestBody @Valid IndustryDTO industryDTO) {
        industryService.create(industryDTO);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @PutMapping
    public ResponseDTO<Void> update(@RequestBody IndustryDTO industryDTO) {
        industryService.update(industryDTO);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @DeleteMapping
    public ResponseDTO<Void> delete(@RequestParam("id") Long id) {
        industryService.delete(id);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @GetMapping
    public ResponseDTO<IndustryDTO> getById(@RequestParam("id") Long id) {
        return ResponseDTO.<IndustryDTO>builder()
            .status(200).msg("OK")
            .data(industryService.getById(id))
            .build();
    }

    @PostMapping("/search")
    public ResponseDTO<PageDTO<IndustryDTO>> search(@RequestBody SearchDTO searchDTO) {
        return ResponseDTO.<PageDTO<IndustryDTO>>builder().status(200).msg("OK")
            .data(industryService.searchService(searchDTO))
            .build();
    }
}
