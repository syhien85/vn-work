package root.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import root.dto.PageDTO;
import root.dto.ResponseDTO;
import root.dto.ReviewResumeDTO;
import root.dto.search.SearchReviewResumeDTO;
import root.service.ReviewResumeService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/review-resume")
public class ReviewResumeController {

    private final ReviewResumeService reviewResumeService;

    @PostMapping
    public ResponseDTO<Void> create(@RequestBody @Valid ReviewResumeDTO reviewResumeDTO) {
        reviewResumeService.create(reviewResumeDTO);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @PutMapping
    public ResponseDTO<Void> update(@RequestBody ReviewResumeDTO reviewResumeDTO) {
        reviewResumeService.update(reviewResumeDTO);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @DeleteMapping
    public ResponseDTO<Void> delete(@RequestParam("id") Long id) {
        reviewResumeService.delete(id);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @GetMapping
    public ResponseDTO<ReviewResumeDTO> getById(@RequestParam("id") Long id) {
        return ResponseDTO.<ReviewResumeDTO>builder()
            .status(200).msg("OK")
            .data(reviewResumeService.getById(id))
            .build();
    }

    @PostMapping("/search")
    public ResponseDTO<PageDTO<ReviewResumeDTO>> search(@RequestBody SearchReviewResumeDTO searchDTO) {
        return ResponseDTO.<PageDTO<ReviewResumeDTO>>builder().status(200).msg("OK")
            .data(reviewResumeService.searchService(searchDTO))
            .build();
    }
}
