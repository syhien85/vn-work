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
import root.dto.JobFunctionParentDTO;
import root.dto.PageDTO;
import root.dto.SearchDTO;
import root.entity.JobFunction;
import root.entity.JobFunctionParent;
import root.exception.DuplicateResourceException;
import root.exception.ResourceNotFoundException;
import root.repository.JobFunctionRepo;
import root.repository.JobFunctionParentRepo;

@RequiredArgsConstructor
@Service
public class JobFunctionParentService {

    private final JobFunctionParentRepo jobFunctionParentRepo;

    @Transactional
    public void create(JobFunctionParentDTO jobFunctionParentDTO) {
        if (jobFunctionParentRepo.existsByName(jobFunctionParentDTO.getName())) {
            throw new DuplicateResourceException(
                ("jobFunctionParent with name [" + jobFunctionParentDTO.getName() + "] already taken")
            );
        }
        JobFunctionParent jobFunctionParent = new ModelMapper().map(jobFunctionParentDTO, JobFunctionParent.class);
        jobFunctionParentRepo.save(jobFunctionParent);
    }

    @Transactional
    public void update(JobFunctionParentDTO jobFunctionParentDTO) {
        JobFunctionParent currentJobFunctionParent = jobFunctionParentRepo.findById(jobFunctionParentDTO.getId()).orElseThrow(
            () -> new ResourceNotFoundException(
                "jobFunctionParent with id [" + jobFunctionParentDTO.getId() + "] not found"
            )
        );
        currentJobFunctionParent.setName(jobFunctionParentDTO.getName());
        jobFunctionParentRepo.save(currentJobFunctionParent);
    }

    @Transactional
    public void delete(Integer id) {
        if (!jobFunctionParentRepo.existsById(id)) {
            throw new ResourceNotFoundException("jobFunctionParent with id [" + id + "] not found");
        }
        jobFunctionParentRepo.deleteById(id);
    }

    public JobFunctionParentDTO getById(Integer id) {
        return jobFunctionParentRepo.findById(id).map(this::convert).orElseThrow(
            () -> new ResourceNotFoundException("jobFunctionParent with id [" + id + "] not found")
        );
    }

    public PageDTO<JobFunctionParentDTO> searchService(SearchDTO searchDTO) {
        Sort sortBy = Sort.by("id").descending();

        if (searchDTO.getSortedField() != null && !searchDTO.getSortedField().isEmpty()) {
            sortBy = Sort.by(searchDTO.getSortedField()).ascending();
        }

        PageRequest pageRequest = PageRequest.of(
            searchDTO.getCurrentPage(), searchDTO.getSize(), sortBy
        );

        Page<JobFunctionParent> page = jobFunctionParentRepo.searchByName(
            "%" + searchDTO.getKeyword() + "%", pageRequest
        );

        return PageDTO.<JobFunctionParentDTO>builder()
            .totalPage(page.getTotalPages())
            .totalElements(page.getTotalElements())
            .data(page.get().map(this::convert).toList())
            .build();
    }

    private JobFunctionParentDTO convert(JobFunctionParent jobFunctionParent) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(jobFunctionParent, JobFunctionParentDTO.class);
    }
}

