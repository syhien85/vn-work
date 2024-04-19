package root.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import root.dto.*;
import root.dto.search.SearchApplicationDTO;
import root.service.ApplicationService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/application")
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping
    public ResponseDTO<Void> create(@RequestBody @Valid ApplicationDTO applicationDTO) {
        applicationService.create(applicationDTO);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    /*@PutMapping
    public ResponseDTO<Void> update(@RequestBody ApplicationDTO applicationDTO) {
        applicationService.update(applicationDTO);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }*/

    @PutMapping
    public ResponseDTO<Void> updateEmployeeStatus(@RequestBody ApplicationDTO applicationDTO) {
        applicationService.updateEmployeeStatus(applicationDTO);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @DeleteMapping
    public ResponseDTO<Void> delete(@RequestParam("id") Long id) {
        applicationService.delete(id);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @GetMapping
    public ResponseDTO<ApplicationDTO> getById(@RequestParam("id") Long id) {
        return ResponseDTO.<ApplicationDTO>builder()
            .status(200).msg("OK")
            .data(applicationService.getById(id))
            .build();
    }

    @PostMapping("/search")
    public ResponseDTO<PageDTO<ApplicationDTO>> search(@RequestBody SearchApplicationDTO searchDTO) {
        return ResponseDTO.<PageDTO<ApplicationDTO>>builder().status(200).msg("OK")
            .data(applicationService.searchService(searchDTO))
            .build();
    }

    @GetMapping("/user-contact-by-resume")
    public ResponseDTO<UserContactDTO> getByResumeId(@RequestParam("id") Long id) {
        return ResponseDTO.<UserContactDTO>builder()
            .status(200).msg("OK")
            .data(applicationService.getUserContactByResumeId(id))
            .build();
    }
}
