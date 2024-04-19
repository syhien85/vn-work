package root.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import root.dto.IndustryDTO;
import root.dto.PageDTO;
import root.dto.SearchDTO;
import root.entity.Industry;
import root.exception.ResourceNotFoundException;
import root.repository.IndustryRepo;

@RequiredArgsConstructor
@Service
public class IndustryService {

    private final IndustryRepo industryRepo;

    @Transactional
    public void create(IndustryDTO industryDTO) {
        Industry industry = new ModelMapper().map(industryDTO, Industry.class);
        industryRepo.save(industry);
    }

    @Transactional
    public void update(IndustryDTO industryDTO) {
        Industry currentIndustry = industryRepo.findById(industryDTO.getId()).orElseThrow(
            () -> new ResourceNotFoundException(
                "industry with id [" + industryDTO.getId() + "] not found"
            )
        );
        currentIndustry.setName(industryDTO.getName());
        industryRepo.save(currentIndustry);
    }

    @Transactional
    public void delete(Long id) {
        if (!industryRepo.existsById(id)) {
            throw new ResourceNotFoundException("industry with id [" + id + "] not found");
        }
        industryRepo.deleteById(id);
    }

    public IndustryDTO getById(Long id) {
        return industryRepo.findById(id).map(this::convert).orElseThrow(
            () -> new ResourceNotFoundException("industry with id [" + id + "] not found")
        );
    }

    public PageDTO<IndustryDTO> searchService(SearchDTO searchDTO) {
        Sort sortBy = Sort.by("id").descending();

        if (searchDTO.getSortedField() != null && !searchDTO.getSortedField().isEmpty()) {
            sortBy = Sort.by(searchDTO.getSortedField()).ascending();
        }

        PageRequest pageRequest = PageRequest.of(
            searchDTO.getCurrentPage(), searchDTO.getSize(), sortBy
        );

        Page<Industry> page = industryRepo.searchByName(
            "%" + searchDTO.getKeyword() + "%", pageRequest
        );

        return PageDTO.<IndustryDTO>builder()
            .totalPage(page.getTotalPages())
            .totalElements(page.getTotalElements())
            .data(page.get().map(this::convert).toList())
            .build();
    }

    private IndustryDTO convert(Industry industry) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(industry, IndustryDTO.class);
    }
}

