package root.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import root.dto.PageDTO;
import root.dto.SearchDTO;
import root.dto.SkillDTO;
import root.entity.Skill;
import root.repository.SkillRepo;

@RequiredArgsConstructor
@Service
public class SkillService {

    private final SkillRepo skillRepo;

    public PageDTO<SkillDTO> searchService(SearchDTO searchDTO) {
        Sort sortBy = Sort.by("id").descending();

        if (searchDTO.getSortedField() != null && !searchDTO.getSortedField().isEmpty()) {
            sortBy = Sort.by(searchDTO.getSortedField()).ascending();
        }

        PageRequest pageRequest = PageRequest.of(
            searchDTO.getCurrentPage(), searchDTO.getSize(), sortBy
        );

        Page<Skill> page = skillRepo.searchByName(
            "%" + searchDTO.getKeyword() + "%", pageRequest
        );

        return PageDTO.<SkillDTO>builder()
            .totalPage(page.getTotalPages())
            .totalElements(page.getTotalElements())
            .data(page.get().map(this::convert).toList())
            .build();
    }

    private SkillDTO convert(Skill skill) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(skill, SkillDTO.class);
    }
}

