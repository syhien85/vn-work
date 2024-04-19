package root.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import root.dto.UserLanguageDTO;
import root.dto.ResponseDTO;
import root.service.UserLanguageService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user-language")
public class UserLanguageController {

    private final UserLanguageService userLanguageService;

    @PostMapping
    public ResponseDTO<Void> create(@RequestBody @Valid UserLanguageDTO userLanguageDTO) {
        userLanguageService.create(userLanguageDTO);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @PutMapping
    public ResponseDTO<Void> update(@RequestBody UserLanguageDTO userLanguageDTO) {
        userLanguageService.update(userLanguageDTO);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @DeleteMapping
    public ResponseDTO<Void> delete(@RequestParam("id") Long id) {
        userLanguageService.delete(id);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @GetMapping
    public ResponseDTO<UserLanguageDTO> getById(@RequestParam("id") Long id) {
        return ResponseDTO.<UserLanguageDTO>builder()
            .status(200).msg("OK")
            .data(userLanguageService.getById(id))
            .build();
    }
}
