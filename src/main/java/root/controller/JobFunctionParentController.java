package root.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import root.dto.JobFunctionParentDTO;
import root.dto.PageDTO;
import root.dto.ResponseDTO;
import root.dto.SearchDTO;
import root.dto.search.SearchJobFunctionDTO;
import root.service.JobFunctionParentService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/job-function-parent")
public class JobFunctionParentController {

    private final JobFunctionParentService jobFunctionParentService;

    @PostMapping
    public ResponseDTO<Void> create(@RequestBody @Valid JobFunctionParentDTO jobFunctionParentDTO) {
        jobFunctionParentService.create(jobFunctionParentDTO);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @PutMapping
    public ResponseDTO<Void> update(@RequestBody JobFunctionParentDTO jobFunctionParentDTO) {
        jobFunctionParentService.update(jobFunctionParentDTO);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @DeleteMapping
    public ResponseDTO<Void> delete(@RequestParam("id") Integer id) {
        jobFunctionParentService.delete(id);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @GetMapping
    public ResponseDTO<JobFunctionParentDTO> getById(@RequestParam("id") Integer id) {
        return ResponseDTO.<JobFunctionParentDTO>builder()
            .status(200).msg("OK")
            .data(jobFunctionParentService.getById(id))
            .build();
    }

    @PostMapping("/search")
    public ResponseDTO<PageDTO<JobFunctionParentDTO>> search(@RequestBody SearchDTO searchDTO) {
        return ResponseDTO.<PageDTO<JobFunctionParentDTO>>builder().status(200).msg("OK")
            .data(jobFunctionParentService.searchService(searchDTO))
            .build();
    }
}
