package root.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import root.dto.ResponseDTO;
import root.dto.UserSummaryDTO;
import root.service.UserSummaryService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user-summary")
public class UserSummaryController {

    private final UserSummaryService userSummaryService;

    @PostMapping
    public ResponseDTO<Void> create(@RequestBody @Valid UserSummaryDTO userSummaryDTO) {
        userSummaryService.create(userSummaryDTO);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @PutMapping
    public ResponseDTO<Void> update(@RequestBody UserSummaryDTO userSummaryDTO) {
        userSummaryService.update(userSummaryDTO);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @DeleteMapping
    public ResponseDTO<Void> delete(@RequestParam("id") Long id) {
        userSummaryService.delete(id);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @GetMapping
    public ResponseDTO<UserSummaryDTO> getById(@RequestParam("id") Long id) {
        return ResponseDTO.<UserSummaryDTO>builder()
            .status(200).msg("OK")
            .data(userSummaryService.getById(id))
            .build();
    }
}
