package root.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import root.dto.ResponseDTO;
import root.dto.WorkingPreferenceDTO;
import root.service.WorkingPreferenceService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/working-preference")
public class WorkingPreferenceController {

    private final WorkingPreferenceService workingPreferenceService;

    @PostMapping
    public ResponseDTO<Void> create(@RequestBody @Valid WorkingPreferenceDTO workingPreferenceDTO) {
        workingPreferenceService.create(workingPreferenceDTO);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @PutMapping
    public ResponseDTO<Void> update(@RequestBody WorkingPreferenceDTO workingPreferenceDTO) {
        workingPreferenceService.update(workingPreferenceDTO);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @DeleteMapping
    public ResponseDTO<Void> delete(@RequestParam("id") Long id) {
        workingPreferenceService.delete(id);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @GetMapping
    public ResponseDTO<WorkingPreferenceDTO> getById(@RequestParam("id") Long id) {
        return ResponseDTO.<WorkingPreferenceDTO>builder()
            .status(200).msg("OK")
            .data(workingPreferenceService.getById(id))
            .build();
    }
}
