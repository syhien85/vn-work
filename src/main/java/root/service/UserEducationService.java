package root.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import root.dto.UserEducationDTO;
import root.entity.UserEducation;
import root.exception.ResourceNotFoundException;
import root.repository.UserEducationRepo;

@RequiredArgsConstructor
@Service
public class UserEducationService {

    private final UserEducationRepo userEducationRepo;

    @Transactional
    public void create(UserEducationDTO userEducationDTO) {
        UserEducation userEducation = new ModelMapper().map(userEducationDTO, UserEducation.class);
        userEducationRepo.save(userEducation);
    }

    @Transactional
    public void update(UserEducationDTO userEducationDTO) {
        UserEducation currentUserEducation = userEducationRepo.findById(userEducationDTO.getId()).orElseThrow(
            () -> new ResourceNotFoundException(
                "userEducation with id [" + userEducationDTO.getId() + "] not found"
            )
        );
        currentUserEducation.setMajor(userEducationDTO.getMajor());
        currentUserEducation.setSchoolName(userEducationDTO.getSchoolName());
        currentUserEducation.setHighestDegree(userEducationDTO.getHighestDegree());
        currentUserEducation.setStartDate(userEducationDTO.getStartDate());
        currentUserEducation.setEndDate(userEducationDTO.getEndDate());
        currentUserEducation.setDescription(userEducationDTO.getDescription());
        userEducationRepo.save(currentUserEducation);
    }

    @Transactional
    public void delete(Long id) {
        if (!userEducationRepo.existsById(id)) {
            throw new ResourceNotFoundException("userEducation with id [" + id + "] not found");
        }
        userEducationRepo.deleteById(id);
    }

    public UserEducationDTO getById(Long id) {
        return userEducationRepo.findById(id).map(this::convert).orElseThrow(
            () -> new ResourceNotFoundException("userEducation with id [" + id + "] not found")
        );
    }

    private UserEducationDTO convert(UserEducation userEducation) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(userEducation, UserEducationDTO.class);
    }
}

