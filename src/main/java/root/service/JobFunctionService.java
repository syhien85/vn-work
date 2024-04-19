package root.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import root.dto.JobFunctionDTO;
import root.dto.PageDTO;
import root.dto.search.SearchJobFunctionDTO;
import root.entity.JobFunction;
import root.exception.DuplicateResourceException;
import root.exception.ResourceNotFoundException;
import root.repository.JobFunctionParentRepo;
import root.repository.JobFunctionRepo;

@RequiredArgsConstructor
@Service
public class JobFunctionService {

    private final JobFunctionRepo jobFunctionRepo;
    private final JobFunctionParentRepo jobFunctionParentRepo;

    @Transactional
    public void create(JobFunctionDTO jobFunctionDTO) {
        if (!jobFunctionParentRepo.existsById(jobFunctionDTO.getJobFunctionParent().getId())) {
            throw new DuplicateResourceException(
                ("jobFunctionParent with id [" + jobFunctionDTO.getJobFunctionParent().getId() + "] not found")
            );
        }

        if (jobFunctionRepo.existsByName(jobFunctionDTO.getName())) {
            throw new DuplicateResourceException(
                ("jobFunction with name [" + jobFunctionDTO.getName() + "] already taken")
            );
        }

        JobFunction jobFunction = new ModelMapper().map(jobFunctionDTO, JobFunction.class);
        jobFunctionRepo.save(jobFunction);
    }

    @Transactional
    public void update(JobFunctionDTO jobFunctionDTO) {
        JobFunction currentJobFunction = jobFunctionRepo.findById(jobFunctionDTO.getId()).orElseThrow(
            () -> new ResourceNotFoundException(
                "jobFunction with id [" + jobFunctionDTO.getId() + "] not found"
            )
        );
        currentJobFunction.setName(jobFunctionDTO.getName());
        jobFunctionRepo.save(currentJobFunction);
    }

    @Transactional
    public void delete(Integer id) {
        if (!jobFunctionRepo.existsById(id)) {
            throw new ResourceNotFoundException("jobFunction with id [" + id + "] not found");
        }
        jobFunctionRepo.deleteById(id);
    }

    public JobFunctionDTO getById(Integer id) {
        return jobFunctionRepo.findById(id).map(this::convert).orElseThrow(
            () -> new ResourceNotFoundException("jobFunction with id [" + id + "] not found")
        );
    }

    public PageDTO<JobFunctionDTO> searchService(SearchJobFunctionDTO searchDTO) {
        Sort sortBy = Sort.by("id").descending();

        if (searchDTO.getSortedField() != null && !searchDTO.getSortedField().isEmpty()) {
            sortBy = Sort.by(searchDTO.getSortedField()).ascending();
        }

        PageRequest pageRequest = PageRequest.of(
            searchDTO.getCurrentPage(), searchDTO.getSize(), sortBy
        );

        String key = "%" + searchDTO.getKeyword() + "%";
        Integer c1 = (searchDTO.getJobFunctionParentId() != null) ? searchDTO.getJobFunctionParentId() : null;

        Page<JobFunction> page = jobFunctionRepo.findAll(pageRequest);

        if (c1 == null) {
            page = jobFunctionRepo.searchBy(key, pageRequest);
        }
        if (c1 != null) {
            page = jobFunctionRepo.searchByC1(key, c1, pageRequest);
        }

        return PageDTO.<JobFunctionDTO>builder()
            .totalPage(page.getTotalPages())
            .totalElements(page.getTotalElements())
            .data(page.get().map(this::convert).toList())
            .build();
    }

    private JobFunctionDTO convert(JobFunction jobFunction) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(jobFunction, JobFunctionDTO.class);
    }
}

