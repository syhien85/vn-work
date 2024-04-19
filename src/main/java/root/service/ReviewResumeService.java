package root.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import root.dto.PageDTO;
import root.dto.ReviewResumeDTO;
import root.dto.search.SearchReviewResumeDTO;
import root.entity.Job;
import root.entity.Resume;
import root.entity.ReviewResume;
import root.enums.EmployeeStatusEnum;
import root.exception.RequestValidationException;
import root.exception.ResourceNotFoundException;
import root.repository.JobRepo;
import root.repository.ResumeRepo;
import root.repository.ReviewResumeRepo;

@RequiredArgsConstructor
@Service
public class ReviewResumeService {

    private final ReviewResumeRepo reviewResumeRepo;
    private final ResumeRepo resumeRepo;
    private final JobRepo jobRepo;

    @Transactional
    public void create(ReviewResumeDTO reviewResumeDTO) {
        if (!resumeRepo.existsById(reviewResumeDTO.getResume().getId())) {
            throw new ResourceNotFoundException(
                "resume with id [" + reviewResumeDTO.getResume().getId() + "] not found"
            );
        }
        if (!jobRepo.existsById(reviewResumeDTO.getJob().getId())) {
            throw new ResourceNotFoundException(
                "job with id [" + reviewResumeDTO.getJob().getId() + "] not found"
            );
        }

        Resume resume = resumeRepo.findById(reviewResumeDTO.getResume().getId()).get();
        Job job = jobRepo.findById(reviewResumeDTO.getJob().getId()).get();

        if (!resume.getResumeStatus().isSearch()) {
            throw new RequestValidationException("resume is not active");
        }
        if (!job.getJobStatus().isActive()) {
            throw new RequestValidationException("job is not active");
        }

//        ReviewResume reviewResume = new ModelMapper().map(reviewResumeDTO, ReviewResume.class);
        ReviewResume reviewResume = ReviewResume
            .builder()
            .employeeStatus(reviewResumeDTO.getEmployeeStatus())
            .resume(resume)
            .job(job)
            .build();
        reviewResumeRepo.save(reviewResume);

        // Send email for user has resume

    }

    @Transactional
    public void update(ReviewResumeDTO reviewResumeDTO) {
        ReviewResume currentReviewResume = reviewResumeRepo.findById(reviewResumeDTO.getId()).orElseThrow(
            () -> new ResourceNotFoundException(
                "reviewResume with id [" + reviewResumeDTO.getId() + "] not found"
            )
        );
        currentReviewResume.setEmployeeStatus(reviewResumeDTO.getEmployeeStatus());
        reviewResumeRepo.save(currentReviewResume);

        // ReviewResumeStatus:
        // EMPLOYER_DECLINED: send email for user owner resumeId
    }

    @Transactional
    public void delete(Long id) {
        if (!reviewResumeRepo.existsById(id)) {
            throw new ResourceNotFoundException("reviewResume with id [" + id + "] not found");
        }
        reviewResumeRepo.deleteById(id);
    }

    public ReviewResumeDTO getById(Long id) {
        return reviewResumeRepo.findById(id).map(this::convert).orElseThrow(
            () -> new ResourceNotFoundException("reviewResume with id [" + id + "] not found")
        );
    }

    public PageDTO<ReviewResumeDTO> searchService(SearchReviewResumeDTO searchDTO) {
        Sort sortBy = Sort.by("id").descending();

        if (searchDTO.getSortedField() != null && !searchDTO.getSortedField().isEmpty()) {
            sortBy = Sort.by(searchDTO.getSortedField()).ascending();
        }

        PageRequest pageRequest = PageRequest.of(
            searchDTO.getCurrentPage(), searchDTO.getSize(), sortBy
        );

//        String key = "%" + searchDTO.getKeyword() + "%";
        Long c1 = searchDTO.getResumeId();
        Long c2 = searchDTO.getJobId();
        EmployeeStatusEnum c3 = searchDTO.getEmployeeStatus();

        Page<ReviewResume> page = reviewResumeRepo.findAll(pageRequest);

        if (c1 == null && c2 == null && c3 == null) {
//            page = reviewResumeRepo.searchBy(pageRequest);
            page = reviewResumeRepo.findAll(pageRequest);
        }
        if (c1 != null && c2 == null && c3 == null) {
            page = reviewResumeRepo.searchByC1(c1, pageRequest);
        }
        if (c1 == null && c2 != null && c3 == null) {
            page = reviewResumeRepo.searchByC2(c2, pageRequest);
        }
        if (c1 == null && c2 == null && c3 != null) {
            page = reviewResumeRepo.searchByC3(c3, pageRequest);
        }
        if (c1 != null && c2 != null && c3 == null) {
            page = reviewResumeRepo.searchByC1AndC2(c1, c2, pageRequest);
        }
        if (c1 != null && c2 == null && c3 != null) {
            page = reviewResumeRepo.searchByC1AndC3(c1, c3, pageRequest);
        }
        if (c1 == null && c2 != null && c3 != null) {
            page = reviewResumeRepo.searchByC2AndC3(c2, c3, pageRequest);
        }
        if (c1 != null && c2 != null && c3 != null) {
            page = reviewResumeRepo.searchByC1AndC2AndC3(c1, c2, c3, pageRequest);
        }

        return PageDTO.<ReviewResumeDTO>builder()
            .totalPage(page.getTotalPages())
            .totalElements(page.getTotalElements())
            .data(page.get().map(this::convert).toList())
            .build();
    }

    private ReviewResumeDTO convert(ReviewResume reviewResume) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(reviewResume, ReviewResumeDTO.class);
    }
}

