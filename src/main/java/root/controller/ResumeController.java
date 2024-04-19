package root.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import root.dto.*;
import root.dto.search.SearchResumeDTO;
import root.service.ResumeService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/resume")
public class ResumeController {

    private final ResumeService resumeService;

    @PostMapping
    public ResponseDTO<Void> create(@RequestBody @Valid ResumeDTO resumeDTO) {
        resumeService.create(resumeDTO);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @PutMapping
    public ResponseDTO<Void> update(@RequestBody ResumeDTO resumeDTO) {
        resumeService.update(resumeDTO);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @PutMapping("/update-status")
    public ResponseDTO<Void> updateStatus(@RequestBody ResumeDTO resumeDTO) {
        resumeService.updateStatus(resumeDTO);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @DeleteMapping
    public ResponseDTO<Void> delete(@RequestParam("id") Long id) {
        resumeService.delete(id);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @GetMapping
    public ResponseDTO<ResumeDTO> getById(@RequestParam("id") Long id) {
        return ResponseDTO.<ResumeDTO>builder()
            .status(200).msg("OK")
            .data(resumeService.getById(id))
            .build();
    }

    @PostMapping("/search")
    public ResponseDTO<PageDTO<ResumeDTO>> search(@RequestBody SearchResumeDTO searchDTO) {
        return ResponseDTO.<PageDTO<ResumeDTO>>builder().status(200).msg("OK")
            .data(resumeService.searchService(searchDTO))
            .build();
    }
}
