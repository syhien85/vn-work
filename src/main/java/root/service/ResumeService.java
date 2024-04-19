package root.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import root.dto.LocationDistrictDTO;
import root.dto.PageDTO;
import root.dto.ResumeDTO;
import root.dto.search.SearchLocationDistrictDTO;
import root.dto.search.SearchResumeDTO;
import root.entity.*;
import root.enums.JobLevelEnum;
import root.exception.RequestValidationException;
import root.exception.ResourceNotFoundException;
import root.repository.ResumeRepo;
import root.repository.UserContactRepo;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ResumeService {

    private final ResumeRepo resumeRepo;
    private final UserContactRepo userContactRepo;

    @Transactional
    public void create(ResumeDTO resumeDTO) {
        Resume resume = new ModelMapper().map(resumeDTO, Resume.class);

        // default active = false, phải duyệt mới public
        resume.setResumeStatus(ResumeStatus.builder().isSearch(false).build());

        resumeRepo.save(resume);
    }

    @Transactional
    public void update(ResumeDTO resumeDTO) {
        Resume currentResume = resumeRepo.findById(resumeDTO.getId()).orElseThrow(
            () -> new ResourceNotFoundException(
                "resume with id [" + resumeDTO.getId() + "] not found"
            )
        );

        // default active = false, phải duyệt mới public
//        currentResume.setResumeStatus(ResumeStatus.builder().isSearch(false).build());

        resumeRepo.save(currentResume);
    }

    @Transactional
    public void updateStatus(ResumeDTO resumeDTO) {
        Resume currentResume = resumeRepo.findById(resumeDTO.getId()).orElseThrow(
            () -> new ResourceNotFoundException(
                "resume with id [" + resumeDTO.getId() + "] not found"
            )
        );

        // check complete Resume UserContact
        // check complete Resume UserProfile
        // check complete Resume WorkingPreference
        // check complete Resume UserSkill // min 1 skill

        boolean isActiveSearch = true;

        Optional<UserContact> userContactsByUserAccountId =
            userContactRepo.findUserContactsByUserAccountId(currentResume.getUser().getAccountId());

        // Resume sẽ đc public (isSearch == true) khi thoả mãn điều kiện sau,
        // isSearch == false thì ko cần check
        if (resumeDTO.getResumeStatus().isSearch()) {

            // bắt buộc phải có: UserContact && UserProfile
            if (currentResume.getUserProfile() == null || userContactsByUserAccountId.isEmpty()) {
                isActiveSearch = false;
                System.out.println(currentResume.getUserProfile());
                System.out.println(userContactsByUserAccountId.isEmpty());
            }

            // 2 trường hợp hợp lệ là:
            // - phải có WorkingPreference && UserSkills
            // - hoặc phải có AttachedCV
            if(
                (currentResume.getWorkingPreference() == null && currentResume.getUserSkills().isEmpty())
                && currentResume.getUserAttachedCV() == null
            ) {
                isActiveSearch = false;

            }

            if (!isActiveSearch) {
                throw new RequestValidationException("Vui lòng cập nhật các trường còn thiếu trong Resume");
            }

            /*currentResume.setResumeStatus(
                ResumeStatus.builder()
                    .isSearch(resumeDTO.getResumeStatus().isSearch())
                    .build()
            );*/
        }
        currentResume.setResumeStatus(
            ResumeStatus.builder()
                .isSearch(resumeDTO.getResumeStatus().isSearch())
                .build()
        );

        resumeRepo.save(currentResume);
    }

    @Transactional
    public void delete(Long id) {
        if (!resumeRepo.existsById(id)) {
            throw new ResourceNotFoundException("resume with id [" + id + "] not found");
        }
        resumeRepo.deleteById(id);
    }

    public ResumeDTO getById(Long id) {
        return resumeRepo.findById(id).map(this::convert).orElseThrow(
            () -> new ResourceNotFoundException("resume with id [" + id + "] not found")
        );
    }

    public PageDTO<ResumeDTO> searchService(SearchResumeDTO searchDTO) {
        Sort sortBy = Sort.by("id").descending();

        if (searchDTO.getSortedField() != null && !searchDTO.getSortedField().isEmpty()) {
            sortBy = Sort.by(searchDTO.getSortedField()).ascending();
        }

        PageRequest pageRequest = PageRequest.of(
            searchDTO.getCurrentPage(), searchDTO.getSize(), sortBy
        );

        String key = "%" + searchDTO.getKeyword() + "%";
        Integer c1 = searchDTO.getLocationCityId();
        Integer c2 = searchDTO.getJobFunctionId();
        Integer c3 = searchDTO.getLanguageId();
        JobLevelEnum c4 = searchDTO.getJobLevel();
        Integer c5 = searchDTO.getYearsExperienceMin() != null ? searchDTO.getYearsExperienceMin() : 0;
        Integer c6 = searchDTO.getYearsExperienceMax() != null ? searchDTO.getYearsExperienceMax() : 40;
        Double c7 = searchDTO.getSalaryMin() != null ? searchDTO.getSalaryMin() : 0;
        Double c8 = searchDTO.getSalaryMax() != null ? searchDTO.getSalaryMax() : 999_000_000;

        Page<Resume> page = resumeRepo.findAll(pageRequest);

        if (c1 == null && c2 == null && c3 == null && c4 == null) {
            page = resumeRepo.searchBy(key, c5, c6, c7, c8, pageRequest);
            System.out.println("1111");
        }
        if (c1 != null && c2 == null && c3 == null && c4 == null) {
            page = resumeRepo.searchByC1(key, c1, c5, c6, c7, c8, pageRequest);
        }
        if (c1 == null && c2 != null && c3 == null && c4 == null) {
            page = resumeRepo.searchByC2(key, c2, c5, c6, c7, c8, pageRequest);
        }
        if (c1 == null && c2 == null && c3 != null && c4 == null) {
            page = resumeRepo.searchByC3(key, c3, c5, c6, c7, c8, pageRequest);
        }
        if (c1 != null && c2 == null && c3 == null && c4 == null) {
            page = resumeRepo.searchByC4(key, c4, c5, c6, c7, c8, pageRequest);
        }
        if (c1 != null && c2 != null && c3 == null && c4 == null) {
            page = resumeRepo.searchByC1AndC2(key, c1, c2, c5, c6, c7, c8, pageRequest);
        }
        if (c1 != null && c2 == null && c3 != null && c4 == null) {
            page = resumeRepo.searchByC1AndC3(key, c1, c3, c5, c6, c7, c8, pageRequest);
        }
        if (c1 != null && c2 == null && c3 == null && c4 != null) {
            page = resumeRepo.searchByC1AndC4(key, c1, c4, c5, c6, c7, c8, pageRequest);
        }
        if (c1 == null && c2 != null && c3 != null && c4 == null) {
            page = resumeRepo.searchByC2AndC3(key, c2, c3, c5, c6, c7, c8, pageRequest);
        }
        if (c1 == null && c2 != null && c3 == null && c4 != null) {
            page = resumeRepo.searchByC2AndC4(key, c2, c4, c5, c6, c7, c8, pageRequest);
        }
        if (c1 == null && c2 == null && c3 != null && c4 != null) {
            page = resumeRepo.searchByC3AndC4(key, c3, c4, c5, c6, c7, c8, pageRequest);
        }
        if (c1 != null && c2 != null && c3 != null && c4 == null) {
            page = resumeRepo.searchByC1AndC2AndC3(key, c1, c2, c3, c5, c6, c7, c8, pageRequest);
        }
        if (c1 != null && c2 != null && c3 == null && c4 != null) {
            page = resumeRepo.searchByC1AndC2AndC4(key, c1, c2, c4, c5, c6, c7, c8, pageRequest);
        }
        if (c1 != null && c2 == null && c3 != null && c4 != null) {
            page = resumeRepo.searchByC1AndC3AndC4(key, c1, c3, c4, c5, c6, c7, c8, pageRequest);
        }
        if (c1 == null && c2 != null && c3 != null && c4 != null) {
            page = resumeRepo.searchByC2AndC3AndC4(key, c2, c3, c4, c5, c6, c7, c8, pageRequest);
        }
        if (c1 != null && c2 != null && c3 != null && c4 != null) {
            page = resumeRepo.searchByC1AndC2AndC3AndC4(key, c1, c2, c3, c4, c5, c6, c7, c8, pageRequest);
        }

        return PageDTO.<ResumeDTO>builder()
            .totalPage(page.getTotalPages())
            .totalElements(page.getTotalElements())
            .data(page.get().map(this::convert).toList())
            .build();
    }

    private ResumeDTO convert(Resume resume) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        // check: company chỉ xem được Resume UserContact khi user đã apply to job
        // ...

        return modelMapper.map(resume, ResumeDTO.class);
    }
}

