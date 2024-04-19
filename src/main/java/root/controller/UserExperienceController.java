package root.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import root.dto.ResponseDTO;
import root.dto.UserExperienceDTO;
import root.service.UserExperienceService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user-experience")
public class UserExperienceController {

    private final UserExperienceService userExperienceService;

    @PostMapping
    public ResponseDTO<Void> create(@RequestBody @Valid UserExperienceDTO userExperienceDTO) {
        userExperienceService.create(userExperienceDTO);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @PutMapping
    public ResponseDTO<Void> update(@RequestBody UserExperienceDTO userExperienceDTO) {
        userExperienceService.update(userExperienceDTO);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @DeleteMapping
    public ResponseDTO<Void> delete(@RequestParam("id") Long id) {
        userExperienceService.delete(id);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @GetMapping
    public ResponseDTO<UserExperienceDTO> getById(@RequestParam("id") Long id) {
        return ResponseDTO.<UserExperienceDTO>builder()
            .status(200).msg("OK")
            .data(userExperienceService.getById(id))
            .build();
    }
}
