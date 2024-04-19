package root.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import root.dto.*;
import root.dto.search.SearchJobFunctionDTO;
import root.service.JobFunctionService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/job-function")
public class JobFunctionController {

    private final JobFunctionService jobFunctionService;

    @PostMapping
    public ResponseDTO<Void> create(@RequestBody @Valid JobFunctionDTO jobFunctionDTO) {
        jobFunctionService.create(jobFunctionDTO);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @PutMapping
    public ResponseDTO<Void> update(@RequestBody JobFunctionDTO jobFunctionDTO) {
        jobFunctionService.update(jobFunctionDTO);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @DeleteMapping
    public ResponseDTO<Void> delete(@RequestParam("id") Integer id) {
        jobFunctionService.delete(id);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @GetMapping
    public ResponseDTO<JobFunctionDTO> getById(@RequestParam("id") Integer id) {
        return ResponseDTO.<JobFunctionDTO>builder()
            .status(200).msg("OK")
            .data(jobFunctionService.getById(id))
            .build();
    }

    @PostMapping("/search")
    public ResponseDTO<PageDTO<JobFunctionDTO>> search(@RequestBody SearchJobFunctionDTO searchDTO) {
        return ResponseDTO.<PageDTO<JobFunctionDTO>>builder().status(200).msg("OK")
            .data(jobFunctionService.searchService(searchDTO))
            .build();
    }
}
