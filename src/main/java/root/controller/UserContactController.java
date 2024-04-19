package root.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import root.dto.ResponseDTO;
import root.dto.UserContactDTO;
import root.service.UserContactService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user-contact")
public class UserContactController {

    private final UserContactService userContactService;

    @PostMapping
    public ResponseDTO<Void> create(@RequestBody @Valid UserContactDTO userContactDTO) {
        userContactService.create(userContactDTO);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @PutMapping
    public ResponseDTO<Void> update(@RequestBody UserContactDTO userContactDTO) {
        userContactService.update(userContactDTO);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @DeleteMapping
    public ResponseDTO<Void> delete(@RequestParam("id") Long id) {
        userContactService.delete(id);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @GetMapping
    public ResponseDTO<UserContactDTO> getById(@RequestParam("id") Long id) {
        return ResponseDTO.<UserContactDTO>builder()
            .status(200).msg("OK")
            .data(userContactService.getById(id))
            .build();
    }
}
