package root.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import root.dto.JobDTO;
import root.dto.PageDTO;
import root.dto.ResponseDTO;
import root.dto.search.SearchJobByCompanyDTO;
import root.dto.search.SearchJobDTO;
import root.service.JobService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/job")
public class JobController {

    private final JobService jobService;

    @PostMapping
    public ResponseDTO<Void> create(@RequestBody @Valid JobDTO jobDTO) {
        jobService.create(jobDTO);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @PutMapping
    public ResponseDTO<Void> update(@RequestBody JobDTO jobDTO) {
        jobService.update(jobDTO);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @PutMapping("/update-approve-expired")
    public ResponseDTO<Void> updateApprove(@RequestBody JobDTO jobDTO) {
        jobService.updateApproveAndExpired(jobDTO);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @PutMapping("/update-status")
    public ResponseDTO<Void> updateStatus(@RequestBody JobDTO jobDTO) {
        jobService.updateJobStatus(jobDTO);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @DeleteMapping
    public ResponseDTO<Void> delete(@RequestParam("id") Long id) {
        jobService.delete(id);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @GetMapping
    public ResponseDTO<JobDTO> getById(@RequestParam("id") Long id) {
        return ResponseDTO.<JobDTO>builder()
            .status(200).msg("OK")
            .data(jobService.getById(id))
            .build();
    }

    @PostMapping("/search")
    public ResponseDTO<PageDTO<JobDTO>> search(@RequestBody SearchJobDTO searchDTO) {
        return ResponseDTO.<PageDTO<JobDTO>>builder().status(200).msg("OK")
            .data(jobService.searchService(searchDTO))
            .build();
    }

    @PostMapping("/search-by-company")
    public ResponseDTO<PageDTO<JobDTO>> searchByCompanyOrCity(@RequestBody SearchJobByCompanyDTO searchDTO) {
        return ResponseDTO.<PageDTO<JobDTO>>builder().status(200).msg("OK")
            .data(jobService.searchByCompanyOrCity(searchDTO))
            .build();
    }
}
