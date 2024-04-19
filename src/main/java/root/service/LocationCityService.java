package root.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import root.dto.LocationCityDTO;
import root.dto.PageDTO;
import root.dto.SearchDTO;
import root.entity.LocationCity;
import root.exception.DuplicateResourceException;
import root.exception.ResourceNotFoundException;
import root.repository.LocationCityRepo;

@RequiredArgsConstructor
@Service
public class LocationCityService {

    private final LocationCityRepo locationCityRepo;

    @Transactional
    public void create(LocationCityDTO locationCityDTO) {
        if (locationCityRepo.existsByName(locationCityDTO.getName())) {
            throw new DuplicateResourceException(
                ("locationCity with name [" + locationCityDTO.getName() + "] already taken")
            );
        }
        LocationCity locationCity = new ModelMapper().map(locationCityDTO, LocationCity.class);
        locationCityRepo.save(locationCity);
    }

    @Transactional
    public void update(LocationCityDTO locationCityDTO) {
        LocationCity currentLocationCity = locationCityRepo.findById(locationCityDTO.getId()).orElseThrow(
            () -> new ResourceNotFoundException(
                "locationCity with id [" + locationCityDTO.getId() + "] not found"
            )
        );
        currentLocationCity.setName(locationCityDTO.getName());
        locationCityRepo.save(currentLocationCity);
    }

    @Transactional
    public void delete(Integer id) {
        if (!locationCityRepo.existsById(id)) {
            throw new ResourceNotFoundException("locationCity with id [" + id + "] not found");
        }
        locationCityRepo.deleteById(id);
    }

    public LocationCityDTO getById(Integer id) {
        return locationCityRepo.findById(id).map(this::convert).orElseThrow(
            () -> new ResourceNotFoundException("locationCity with id [" + id + "] not found")
        );
    }

    public PageDTO<LocationCityDTO> searchService(SearchDTO searchDTO) {
        Sort sortBy = Sort.by("id").descending();

        if (searchDTO.getSortedField() != null && !searchDTO.getSortedField().isEmpty()) {
            sortBy = Sort.by(searchDTO.getSortedField()).ascending();
        }

        PageRequest pageRequest = PageRequest.of(
            searchDTO.getCurrentPage(), searchDTO.getSize(), sortBy
        );

        Page<LocationCity> page = locationCityRepo.searchByName(
            "%" + searchDTO.getKeyword() + "%", pageRequest
        );

        return PageDTO.<LocationCityDTO>builder()
            .totalPage(page.getTotalPages())
            .totalElements(page.getTotalElements())
            .data(page.get().map(this::convert).toList())
            .build();
    }

    private LocationCityDTO convert(LocationCity locationCity) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(locationCity, LocationCityDTO.class);
    }
}

