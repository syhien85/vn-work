package root.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import root.dto.LanguageDTO;
import root.dto.PageDTO;
import root.dto.SearchDTO;
import root.entity.Language;
import root.exception.DuplicateResourceException;
import root.exception.ResourceNotFoundException;
import root.repository.LanguageRepo;

@RequiredArgsConstructor
@Service
public class LanguageService {

    private final LanguageRepo languageRepo;

    @Transactional
    public void create(LanguageDTO languageDTO) {
        if (languageRepo.existsByName(languageDTO.getName())) {
            throw new DuplicateResourceException(
                ("language with name [" + languageDTO.getName() + "] already taken")
            );
        }
        Language language = new ModelMapper().map(languageDTO, Language.class);
        languageRepo.save(language);
    }

    @Transactional
    public void update(LanguageDTO languageDTO) {
        Language currentLanguage = languageRepo.findById(languageDTO.getId()).orElseThrow(
            () -> new ResourceNotFoundException(
                "language with id [" + languageDTO.getId() + "] not found"
            )
        );
        currentLanguage.setName(languageDTO.getName());
        languageRepo.save(currentLanguage);
    }

    @Transactional
    public void delete(Integer id) {
        if (!languageRepo.existsById(id)) {
            throw new ResourceNotFoundException("language with id [" + id + "] not found");
        }
        languageRepo.deleteById(id);
    }

    public LanguageDTO getById(Integer id) {
        return languageRepo.findById(id).map(this::convert).orElseThrow(
            () -> new ResourceNotFoundException("language with id [" + id + "] not found")
        );
    }

    public PageDTO<LanguageDTO> searchService(SearchDTO searchDTO) {
        Sort sortBy = Sort.by("id").descending();

        if (searchDTO.getSortedField() != null && !searchDTO.getSortedField().isEmpty()) {
            sortBy = Sort.by(searchDTO.getSortedField()).ascending();
        }

        PageRequest pageRequest = PageRequest.of(
            searchDTO.getCurrentPage(), searchDTO.getSize(), sortBy
        );

        Page<Language> page = languageRepo.searchByName(
            "%" + searchDTO.getKeyword() + "%", pageRequest
        );

        return PageDTO.<LanguageDTO>builder()
            .totalPage(page.getTotalPages())
            .totalElements(page.getTotalElements())
            .data(page.get().map(this::convert).toList())
            .build();
    }

    private LanguageDTO convert(Language language) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(language, LanguageDTO.class);
    }
}

