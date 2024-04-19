package root.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import root.dto.ResponseDTO;
import root.dto.UserEducationDTO;
import root.service.UserEducationService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user-education")
public class UserEducationController {

    private final UserEducationService userEducationService;

    @PostMapping
    public ResponseDTO<Void> create(@RequestBody @Valid UserEducationDTO userEducationDTO) {
        userEducationService.create(userEducationDTO);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @PutMapping
    public ResponseDTO<Void> update(@RequestBody UserEducationDTO userEducationDTO) {
        userEducationService.update(userEducationDTO);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @DeleteMapping
    public ResponseDTO<Void> delete(@RequestParam("id") Long id) {
        userEducationService.delete(id);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @GetMapping
    public ResponseDTO<UserEducationDTO> getById(@RequestParam("id") Long id) {
        return ResponseDTO.<UserEducationDTO>builder()
            .status(200).msg("OK")
            .data(userEducationService.getById(id))
            .build();
    }
}
