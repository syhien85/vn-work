package root.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import root.dto.UserLanguageDTO;
import root.entity.UserLanguage;
import root.exception.DuplicateResourceException;
import root.exception.ResourceNotFoundException;
import root.repository.LanguageRepo;
import root.repository.UserLanguageRepo;

@RequiredArgsConstructor
@Service
public class UserLanguageService {

    private final UserLanguageRepo userLanguageRepo;
    private final LanguageRepo languageRepo;

    @Transactional
    public void create(UserLanguageDTO userLanguageDTO) {
        if (languageRepo.existsByName(userLanguageDTO.getLanguage().getName())) {
            throw new DuplicateResourceException(
                ("userLanguage with name [" + userLanguageDTO.getLanguage().getName() + "] already taken")
            );
        }
        UserLanguage userLanguage = new ModelMapper().map(userLanguageDTO, UserLanguage.class);
        userLanguageRepo.save(userLanguage);
    }

    @Transactional
    public void update(UserLanguageDTO userLanguageDTO) {
        UserLanguage currentUserLanguage = userLanguageRepo.findById(userLanguageDTO.getId()).orElseThrow(
            () -> new ResourceNotFoundException(
                "userLanguage with id [" + userLanguageDTO.getId() + "] not found"
            )
        );
        currentUserLanguage.setLevel(userLanguageDTO.getLevel());
        userLanguageRepo.save(currentUserLanguage);
    }

    @Transactional
    public void delete(Long id) {
        if (!userLanguageRepo.existsById(id)) {
            throw new ResourceNotFoundException("userLanguage with id [" + id + "] not found");
        }
        userLanguageRepo.deleteById(id);
    }

    public UserLanguageDTO getById(Long id) {
        return userLanguageRepo.findById(id).map(this::convert).orElseThrow(
            () -> new ResourceNotFoundException("userLanguage with id [" + id + "] not found")
        );
    }

    private UserLanguageDTO convert(UserLanguage userLanguage) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(userLanguage, UserLanguageDTO.class);
    }
}

