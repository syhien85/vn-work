package root.controller;

import root.dto.LanguageDTO;
import root.dto.PageDTO;
import root.dto.ResponseDTO;
import root.dto.SearchDTO;
import root.service.LanguageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/language")
public class LanguageController {

    private final LanguageService languageService;

    @PostMapping
    public ResponseDTO<Void> create(@RequestBody @Valid LanguageDTO languageDTO) {
        languageService.create(languageDTO);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @PutMapping
    public ResponseDTO<Void> update(@RequestBody LanguageDTO languageDTO) {
        languageService.update(languageDTO);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @DeleteMapping
    public ResponseDTO<Void> delete(@RequestParam("id") Integer id) {
        languageService.delete(id);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @GetMapping
    public ResponseDTO<LanguageDTO> getById(@RequestParam("id") Integer id) {
        return ResponseDTO.<LanguageDTO>builder()
            .status(200).msg("OK")
            .data(languageService.getById(id))
            .build();
    }

    @PostMapping("/search")
    public ResponseDTO<PageDTO<LanguageDTO>> search(@RequestBody SearchDTO searchDTO) {
        return ResponseDTO.<PageDTO<LanguageDTO>>builder().status(200).msg("OK")
            .data(languageService.searchService(searchDTO))
            .build();
    }
}
