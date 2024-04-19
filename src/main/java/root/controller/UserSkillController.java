package root.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import root.dto.UserSkillDTO;
import root.dto.ResponseDTO;
import root.service.UserSkillService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user-skill")
public class UserSkillController {

    private final UserSkillService userSkillService;

    @PostMapping
    public ResponseDTO<Void> create(@RequestBody @Valid UserSkillDTO userSkillDTO) {
        userSkillService.create(userSkillDTO);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @PutMapping
    public ResponseDTO<Void> update(@RequestBody UserSkillDTO userSkillDTO) {
        userSkillService.update(userSkillDTO);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @DeleteMapping
    public ResponseDTO<Void> delete(@RequestParam("id") Long id) {
        userSkillService.delete(id);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @GetMapping
    public ResponseDTO<UserSkillDTO> getById(@RequestParam("id") Long id) {
        return ResponseDTO.<UserSkillDTO>builder()
            .status(200).msg("OK")
            .data(userSkillService.getById(id))
            .build();
    }
}
