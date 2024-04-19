package root.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import root.dto.UserExperienceDTO;
import root.entity.UserExperience;
import root.exception.ResourceNotFoundException;
import root.repository.UserExperienceRepo;

@RequiredArgsConstructor
@Service
public class UserExperienceService {

    private final UserExperienceRepo userExperienceRepo;

    @Transactional
    public void create(UserExperienceDTO userExperienceDTO) {
        // companyName, companyId
        UserExperience userExperience = new ModelMapper().map(userExperienceDTO, UserExperience.class);
        userExperienceRepo.save(userExperience);
    }

    @Transactional
    public void update(UserExperienceDTO userExperienceDTO) {
        UserExperience currentUserExperience = userExperienceRepo.findById(userExperienceDTO.getId()).orElseThrow(
            () -> new ResourceNotFoundException(
                "userExperience with id [" + userExperienceDTO.getId() + "] not found"
            )
        );
        currentUserExperience.setJobTitle(userExperienceDTO.getJobTitle());
//        currentUserExperience.setCompany(userExperienceDTO.getCompany());
        currentUserExperience.setStartDate(userExperienceDTO.getStartDate());
        currentUserExperience.setEndDate(userExperienceDTO.getEndDate());
        currentUserExperience.setDescription(userExperienceDTO.getDescription());
        userExperienceRepo.save(currentUserExperience);
    }

    @Transactional
    public void delete(Long id) {
        if (!userExperienceRepo.existsById(id)) {
            throw new ResourceNotFoundException("userExperience with id [" + id + "] not found");
        }
        userExperienceRepo.deleteById(id);
    }

    public UserExperienceDTO getById(Long id) {
        return userExperienceRepo.findById(id).map(this::convert).orElseThrow(
            () -> new ResourceNotFoundException("userExperience with id [" + id + "] not found")
        );
    }

    private UserExperienceDTO convert(UserExperience userExperience) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(userExperience, UserExperienceDTO.class);
    }
}

