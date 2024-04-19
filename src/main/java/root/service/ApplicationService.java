package root.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import root.dto.*;
import root.dto.search.SearchApplicationDTO;
import root.entity.*;
import root.enums.ApplicationStatusEnum;
import root.exception.RequestValidationException;
import root.exception.ResourceNotFoundException;
import root.repository.ApplicationRepo;
import root.repository.JobRepo;
import root.repository.ResumeRepo;

@RequiredArgsConstructor
@Service
public class ApplicationService {

    private final ApplicationRepo applicationRepo;
    private final ResumeRepo resumeRepo;
    private final JobRepo jobRepo;

    @Transactional
    public void create(ApplicationDTO applicationDTO) {
        if (!resumeRepo.existsById(applicationDTO.getResume().getId())) {
            throw new ResourceNotFoundException(
                "resume with id [" + applicationDTO.getResume().getId() + "] not found"
            );
        }
        if (!jobRepo.existsById(applicationDTO.getJob().getId())) {
            throw new ResourceNotFoundException(
                "job with id [" + applicationDTO.getJob().getId() + "] not found"
            );
        }

        Resume resume = resumeRepo.findById(applicationDTO.getResume().getId()).get();
        Job job = jobRepo.findById(applicationDTO.getJob().getId()).get();

        if (!resume.getResumeStatus().isSearch()) {
            throw new RequestValidationException("resume is not active");
        }
        if (!job.getJobStatus().isActive()) {
            throw new RequestValidationException("job is not active");
        }

        Application application = Application
            .builder()
            .applicationStatus(ApplicationStatusEnum.APPLIED)
            .resume(resume)
            .job(job)
            .build();
        applicationRepo.save(application);

        // Send email applied for job of company
    }

    /*@Transactional
    public void update(ApplicationDTO applicationDTO) {
        Application currentApplication = applicationRepo.findById(applicationDTO.getId()).orElseThrow(
            () -> new ResourceNotFoundException(
                "application with id [" + applicationDTO.getId() + "] not found"
            )
        );
        currentApplication.setApplicationStatus(applicationDTO.getApplicationStatus());
        currentApplication.setEmployeeStatus(applicationDTO.getEmployeeStatus());
        applicationRepo.save(currentApplication);
    }*/

    @Transactional
    public void updateEmployeeStatus(ApplicationDTO applicationDTO) {
        Application currentApplication = applicationRepo.findById(applicationDTO.getId()).orElseThrow(
            () -> new ResourceNotFoundException(
                "application with id [" + applicationDTO.getId() + "] not found"
            )
        );
        currentApplication.setEmployeeStatus(applicationDTO.getEmployeeStatus());
        applicationRepo.save(currentApplication);

        // ApplicationStatus:
        // APPLIED: send email for company owner jobId
        // EMPLOYER_ACCEPT: send email for user owner resumeId

        // ApplicationStatus:
        // EMPLOYER_DECLINED: send email for user owner resumeId
    }

    @Transactional
    public void delete(Long id) {
        if (!applicationRepo.existsById(id)) {
            throw new ResourceNotFoundException("application with id [" + id + "] not found");
        }
        applicationRepo.deleteById(id);
    }

    public ApplicationDTO getById(Long id) {
        // chưa: mỗi cty chỉ đc xem tối đa (n) resume/day (trừ các resume đã apply vào job (cty) đó)

        return applicationRepo.findById(id).map(this::convert).orElseThrow(
            () -> new ResourceNotFoundException("application with id [" + id + "] not found")
        );
    }

    public PageDTO<ApplicationDTO> searchService(SearchApplicationDTO searchDTO) {
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

        Page<Application> page = applicationRepo.findAll(pageRequest);

        if (c1 == null && c2 == null) {
            page = applicationRepo.findAll(pageRequest);
        }
        if (c1 != null && c2 == null) {
            page = applicationRepo.searchByC1(c1, pageRequest);
        }
        if (c1 == null && c2 != null) {
            page = applicationRepo.searchByC2(c2, pageRequest);
        }
        if (c1 != null && c2 != null) {
            page = applicationRepo.searchByC1AndC2(c1, c2, pageRequest);
        }

        return PageDTO.<ApplicationDTO>builder()
            .totalPage(page.getTotalPages())
            .totalElements(page.getTotalElements())
            .data(page.get().map(this::convert).toList())
            .build();
    }

    public UserContactDTO getUserContactByResumeId(Long id) {
        if(!resumeRepo.existsById(id)) {
            throw new ResourceNotFoundException("resume with id [" + id + "] not found");
        }

        UserContact userContactByResumeId = applicationRepo.findUserContactByResumeId(id).orElseThrow(
            () -> new ResourceNotFoundException("userContact with resumeId [" + id + "] not found")
        );

        UserContactDTO userContactDTO = UserContactDTO
            .builder()
            .id(userContactByResumeId.getId())
            .email(userContactByResumeId.getEmail())
            .phone(userContactByResumeId.getPhone())
            .birthdate(userContactByResumeId.getBirthdate())
            .gender(userContactByResumeId.getGender())
            .maritalStatus(userContactByResumeId.getMaritalStatus())
            .nationality(userContactByResumeId.getNationality())
            .location(
                new ModelMapper().map(userContactByResumeId.getLocation(), LocationDTO.class)
            )
            .build();
        return userContactDTO;
    }

    private ApplicationDTO convert(Application application) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(application, ApplicationDTO.class);
    }
}

