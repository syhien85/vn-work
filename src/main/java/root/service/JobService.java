package root.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import root.dto.JobDTO;
import root.dto.PageDTO;
import root.dto.search.SearchJobByCompanyDTO;
import root.dto.search.SearchJobDTO;
import root.entity.*;
import root.enums.JobLevelEnum;
import root.enums.TypeWorkingEnum;
import root.exception.RequestValidationException;
import root.exception.ResourceNotFoundException;
import root.repository.JobRepo;

import java.text.DecimalFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@RequiredArgsConstructor
@Service
public class JobService {

    private final JobRepo jobRepo;
//    private final SkillRepo skillRepo;

    @Transactional
    public void create(JobDTO jobDTO) {
        Job job = new ModelMapper().map(jobDTO, Job.class);

        setPrettySalary(job);

        // default active = false, phải duyệt mới public
        job.setJobStatus(JobStatus.builder().isActive(false).build());

        jobRepo.save(job);
    }

    @Transactional
    public void update(JobDTO jobDTO) {
        Job currentJob = jobRepo.findById(jobDTO.getId()).orElseThrow(
            () -> new ResourceNotFoundException(
                "job with id [" + jobDTO.getId() + "] not found"
            )
        );

        currentJob.setJobSummary(
            new ModelMapper().map(jobDTO.getJobSummary(), JobSummary.class)
        );
        setPrettySalary(currentJob);

        currentJob.setJobDetail(
            new ModelMapper().map(jobDTO.getJobDetail(), JobDetail.class)
        );

        /*currentJob.setJobSkills(
            jobDTO.getJobSkills().stream().map(
                jobSkillDTO -> new ModelMapper().map(jobSkillDTO, JobSkill.class)
            ).toList()
        );*/

        jobRepo.save(currentJob);
    }

    private static void setPrettySalary(Job job) {
        JobSummary jobSummary = job.getJobSummary();
        double salaryMin = jobSummary.getSalaryMin() <= 0 ? 0 : jobSummary.getSalaryMin();
        double salaryMax = jobSummary.getSalaryMax() <= 0 ? 99_000_000 : jobSummary.getSalaryMax();
        DecimalFormat decimalFormat = new DecimalFormat("#.###########");
        jobSummary.setSalary(decimalFormat.format(salaryMin) + " - " + decimalFormat.format(salaryMax));

        if (jobSummary.getIsSalaryVisible()) {
            jobSummary.setPrettySalary(jobSummary.getSalary());
        } else {
            jobSummary.setPrettySalary("Thương lượng");
        }
    }

    @Transactional
    public void updateApproveAndExpired(JobDTO jobDTO) {
        Job currentJob = jobRepo.findById(jobDTO.getId()).orElseThrow(
            () -> new ResourceNotFoundException(
                "job with id [" + jobDTO.getId() + "] not found"
            )
        );

        // check complete JobDetail
        // check complete JobSummary
        // check complete JobSkill // min 1 skill
        if (
            currentJob.getJobDetail() == null
                || currentJob.getJobSummary() == null
                || currentJob.getJobSkills().isEmpty()
        ) {
            throw new RequestValidationException("JobDetail or JobSummary or JobSkill is not complete");
        }

        JobStatus jobStatus = currentJob.getJobStatus();
        jobStatus.setApproved(true);
        jobStatus.setApprovedOn(Date.from(Instant.now()));

        Date expiredOn = jobDTO.getJobStatus().getExpiredOn();
        Date after30Day = Date.from(jobStatus.getApprovedOn().toInstant().plus(30, ChronoUnit.DAYS));

        if (expiredOn.before(Date.from(Instant.now()))) {
            throw new RequestValidationException("ExpiredOn phải sau thời gian hiện tại");
        }

        if (expiredOn.after(after30Day)) {
            jobStatus.setExpiredOn(after30Day); // Expired max: 30 day
        } else {
            jobStatus.setExpiredOn(expiredOn);
        }

//        jobStatus.setActive(jobDTO.getJobStatus().isActive());

        jobRepo.save(currentJob);
    }

    @Transactional
    public void updateJobStatus(JobDTO jobDTO) {
        Job currentJob = jobRepo.findById(jobDTO.getId()).orElseThrow(
            () -> new ResourceNotFoundException(
                "job with id [" + jobDTO.getId() + "] not found"
            )
        );

        JobStatus jobStatus = currentJob.getJobStatus();
        jobStatus.setActive(jobDTO.getJobStatus().isActive());

        jobRepo.save(currentJob);
    }

    @Transactional
    public void delete(Long id) {
        if (!jobRepo.existsById(id)) {
            throw new ResourceNotFoundException("job with id [" + id + "] not found");
        }
        jobRepo.deleteById(id);
    }

    public JobDTO getById(Long id) {
        return jobRepo.findById(id).map(this::convert).orElseThrow(
            () -> new ResourceNotFoundException("job with id [" + id + "] not found")
        );
    }

    public PageDTO<JobDTO> searchService(SearchJobDTO searchDTO) {
        Sort sortBy = Sort.by("id").descending();

        if (searchDTO.getSortedField() != null && !searchDTO.getSortedField().isEmpty()) {
            sortBy = Sort.by(searchDTO.getSortedField()).ascending();
        }

        PageRequest pageRequest = PageRequest.of(
            searchDTO.getCurrentPage(), searchDTO.getSize(), sortBy
        );

        String key = "%" + searchDTO.getKeyword() + "%";
        Integer c2 = searchDTO.getJobFunctionId();
        Integer c1 = searchDTO.getLocationCityId();
        JobLevelEnum c3 = searchDTO.getJobLevel();
        TypeWorkingEnum c4 = searchDTO.getTypeWorking();
        Integer c5 = searchDTO.getYearsExperienceMin() != null ? searchDTO.getYearsExperienceMin() : 0;
        Integer c6 = searchDTO.getYearsExperienceMax() != null ? searchDTO.getYearsExperienceMax() : 40;
        Double c7 = searchDTO.getSalaryMin() != null ? searchDTO.getSalaryMin() : 0;
        Double c8 = searchDTO.getSalaryMax() != null ? searchDTO.getSalaryMax() : 999_000_000;

        Page<Job> page = jobRepo.findAll(pageRequest);

        if (c1 == null && c2 == null && c3 == null && c4 == null) {
            page = jobRepo.searchBy(key, c5, c6, c7, c8, pageRequest);
        }
        if (c1 != null && c2 == null && c3 == null && c4 == null) {
            page = jobRepo.searchByC1(key, c1, c5, c6, c7, c8, pageRequest);
//            page = jobRepo.searchByC1(c1, pageRequest);
        }
        if (c1 == null && c2 != null && c3 == null && c4 == null) {
            page = jobRepo.searchByC2(key, c2, c5, c6, c7, c8, pageRequest);
        }
        if (c1 == null && c2 == null && c3 != null && c4 == null) {
            page = jobRepo.searchByC3(key, c3, c5, c6, c7, c8, pageRequest);
        }
        if (c1 != null && c2 == null && c3 == null && c4 == null) {
            page = jobRepo.searchByC4(key, c4, c5, c6, c7, c8, pageRequest);
        }
        if (c1 != null && c2 != null && c3 == null && c4 == null) {
            page = jobRepo.searchByC1AndC2(key, c1, c2, c5, c6, c7, c8, pageRequest);
        }
        if (c1 != null && c2 == null && c3 != null && c4 == null) {
            page = jobRepo.searchByC1AndC3(key, c1, c3, c5, c6, c7, c8, pageRequest);
        }
        if (c1 != null && c2 == null && c3 == null && c4 != null) {
            page = jobRepo.searchByC1AndC4(key, c1, c4, c5, c6, c7, c8, pageRequest);
        }
        if (c1 == null && c2 != null && c3 != null && c4 == null) {
            page = jobRepo.searchByC2AndC3(key, c2, c3, c5, c6, c7, c8, pageRequest);
        }
        if (c1 == null && c2 != null && c3 == null && c4 != null) {
            page = jobRepo.searchByC2AndC4(key, c2, c4, c5, c6, c7, c8, pageRequest);
        }
        if (c1 == null && c2 == null && c3 != null && c4 != null) {
            page = jobRepo.searchByC3AndC4(key, c3, c4, c5, c6, c7, c8, pageRequest);
        }
        if (c1 != null && c2 != null && c3 != null && c4 == null) {
            page = jobRepo.searchByC1AndC2AndC3(key, c1, c2, c3, c5, c6, c7, c8, pageRequest);
        }
        if (c1 != null && c2 != null && c3 == null && c4 != null) {
            page = jobRepo.searchByC1AndC2AndC4(key, c1, c2, c4, c5, c6, c7, c8, pageRequest);
        }
        if (c1 != null && c2 == null && c3 != null && c4 != null) {
            page = jobRepo.searchByC1AndC3AndC4(key, c1, c3, c4, c5, c6, c7, c8, pageRequest);
        }
        if (c1 == null && c2 != null && c3 != null && c4 != null) {
            page = jobRepo.searchByC2AndC3AndC4(key, c2, c3, c4, c5, c6, c7, c8, pageRequest);
        }
        if (c1 != null && c2 != null && c3 != null && c4 != null) {
            page = jobRepo.searchByC1AndC2AndC3AndC4(key, c1, c2, c3, c4, c5, c6, c7, c8, pageRequest);
        }

        return PageDTO.<JobDTO>builder()
            .totalPage(page.getTotalPages())
            .totalElements(page.getTotalElements())
            .data(page.get().map(this::convert).toList())
            .build();
    }

    public PageDTO<JobDTO> searchByCompanyOrCity(SearchJobByCompanyDTO searchDTO) {
        Sort sortBy = Sort.by("id").descending();

        if (searchDTO.getSortedField() != null && !searchDTO.getSortedField().isEmpty()) {
            sortBy = Sort.by(searchDTO.getSortedField()).ascending();
        }

        PageRequest pageRequest = PageRequest.of(
            searchDTO.getCurrentPage(), searchDTO.getSize(), sortBy
        );

//        String key = "%" + searchDTO.getKeyword() + "%";
        Long d1 = searchDTO.getCompanyId();
        Integer d2 = searchDTO.getLocationCityId();

        Page<Job> page = jobRepo.findAll(pageRequest);

        if (d1 == null && d2 == null) {
            page = jobRepo.findAll(pageRequest);
        }
        if (d1 != null && d2 == null) {
            page = jobRepo.searchByD1(d1, pageRequest);
        }
        if (d1 == null && d2 != null) {
            page = jobRepo.searchByD2(d2, pageRequest);
        }
        if (d1 != null && d2 != null) {
            page = jobRepo.searchByD1AndD2(d1, d2, pageRequest);
        }

        return PageDTO.<JobDTO>builder()
            .totalPage(page.getTotalPages())
            .totalElements(page.getTotalElements())
            .data(page.get().map(this::convert).toList())
            .build();
    }

    private JobDTO convert(Job job) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(job, JobDTO.class);
    }
}

