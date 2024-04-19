package root.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import root.dto.UserSummaryDTO;
import root.entity.UserSummary;
import root.exception.ResourceNotFoundException;
import root.repository.UserSummaryRepo;

@RequiredArgsConstructor
@Service
public class UserSummaryService {

    private final UserSummaryRepo userSummaryRepo;

    @Transactional
    public void create(UserSummaryDTO userSummaryDTO) {
        UserSummary userSummary = new ModelMapper().map(userSummaryDTO, UserSummary.class);
        userSummaryRepo.save(userSummary);
    }

    @Transactional
    public void update(UserSummaryDTO userSummaryDTO) {
        UserSummary currentUserSummary = userSummaryRepo.findById(userSummaryDTO.getId()).orElseThrow(
            () -> new ResourceNotFoundException(
                "userSummary with id [" + userSummaryDTO.getId() + "] not found"
            )
        );
        currentUserSummary.setSummary(userSummaryDTO.getSummary());
        userSummaryRepo.save(currentUserSummary);
    }

    @Transactional
    public void delete(Long id) {
        if (!userSummaryRepo.existsById(id)) {
            throw new ResourceNotFoundException("userSummary with id [" + id + "] not found");
        }
        userSummaryRepo.deleteById(id);
    }

    public UserSummaryDTO getById(Long id) {
        return userSummaryRepo.findById(id).map(this::convert).orElseThrow(
            () -> new ResourceNotFoundException("userSummary with id [" + id + "] not found")
        );
    }

    private UserSummaryDTO convert(UserSummary userSummary) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(userSummary, UserSummaryDTO.class);
    }
}

