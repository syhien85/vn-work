package root.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import root.dto.IndustryDTO;
import root.dto.WorkingPreferenceDTO;
import root.entity.Industry;
import root.entity.JobFunction;
import root.entity.WorkingPreference;
import root.exception.ResourceNotFoundException;
import root.repository.WorkingPreferenceRepo;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class WorkingPreferenceService {

    private final WorkingPreferenceRepo workingPreferenceRepo;

    @Transactional
    public void create(WorkingPreferenceDTO workingPreferenceDTO) {
        WorkingPreference workingPreference = new ModelMapper().map(workingPreferenceDTO, WorkingPreference.class);
        workingPreferenceRepo.save(workingPreference);
    }

    @Transactional
    public void update(WorkingPreferenceDTO workingPreferenceDTO) {
        WorkingPreference currentWorkingPreference = workingPreferenceRepo.findById(workingPreferenceDTO.getId()).orElseThrow(
            () -> new ResourceNotFoundException(
                "workingPreference with id [" + workingPreferenceDTO.getId() + "] not found"
            )
        );
//        currentWorkingPreference.setWorkingPreferenceLocations(workingPreferenceDTO.getWorkingPreferenceLocations());
        currentWorkingPreference.setIsRelocate(workingPreferenceDTO.getIsRelocate());
        currentWorkingPreference.setJobLevel(workingPreferenceDTO.getJobLevel());
        currentWorkingPreference.setBenefits(workingPreferenceDTO.getBenefits());
        currentWorkingPreference.setSalary(workingPreferenceDTO.getSalary());

        currentWorkingPreference.setJobFunction(
            new ModelMapper().map(workingPreferenceDTO.getJobFunction(), JobFunction.class)
        );

        currentWorkingPreference.setIndustries(
            workingPreferenceDTO.getIndustries().stream().map(
                industryDTO -> new ModelMapper().map(industryDTO, Industry.class)
            ).toList()
        );

        workingPreferenceRepo.save(currentWorkingPreference);
    }

    @Transactional
    public void delete(Long id) {
        if (!workingPreferenceRepo.existsById(id)) {
            throw new ResourceNotFoundException("workingPreference with id [" + id + "] not found");
        }
        workingPreferenceRepo.deleteById(id);
    }

    public WorkingPreferenceDTO getById(Long id) {
        return workingPreferenceRepo.findById(id).map(this::convert).orElseThrow(
            () -> new ResourceNotFoundException("workingPreference with id [" + id + "] not found")
        );
    }

    private WorkingPreferenceDTO convert(WorkingPreference workingPreference) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(workingPreference, WorkingPreferenceDTO.class);
    }
}

