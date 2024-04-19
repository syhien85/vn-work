package root.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import root.dto.ResponseDTO;
import root.dto.JobSkillDTO;
import root.service.JobSkillService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/job-skill")
public class JobSkillController {

    private final JobSkillService jobSkillService;

    @PostMapping
    public ResponseDTO<Void> create(@RequestBody @Valid JobSkillDTO jobSkillDTO) {
        jobSkillService.create(jobSkillDTO);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @PutMapping
    public ResponseDTO<Void> update(@RequestBody JobSkillDTO jobSkillDTO) {
        jobSkillService.update(jobSkillDTO);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @DeleteMapping
    public ResponseDTO<Void> delete(@RequestParam("id") Long id) {
        jobSkillService.delete(id);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @GetMapping
    public ResponseDTO<JobSkillDTO> getById(@RequestParam("id") Long id) {
        return ResponseDTO.<JobSkillDTO>builder()
            .status(200).msg("OK")
            .data(jobSkillService.getById(id))
            .build();
    }
}
