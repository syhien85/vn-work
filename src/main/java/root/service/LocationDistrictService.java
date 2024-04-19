package root.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import root.dto.LocationDistrictDTO;
import root.dto.PageDTO;
import root.dto.SearchDTO;
import root.dto.search.SearchLocationDistrictDTO;
import root.entity.LocationDistrict;
import root.exception.DuplicateResourceException;
import root.exception.ResourceNotFoundException;
import root.repository.LocationDistrictRepo;

@RequiredArgsConstructor
@Service
public class LocationDistrictService {

    private final LocationDistrictRepo locationDistrictRepo;

    @Transactional
    public void create(LocationDistrictDTO locationDistrictDTO) {
        if (locationDistrictRepo.existsByName(locationDistrictDTO.getName())) {
            throw new DuplicateResourceException(
                ("locationDistrict with name [" + locationDistrictDTO.getName() + "] already taken")
            );
        }
        LocationDistrict locationDistrict = new ModelMapper().map(locationDistrictDTO, LocationDistrict.class);
        locationDistrictRepo.save(locationDistrict);
    }

    @Transactional
    public void update(LocationDistrictDTO locationDistrictDTO) {
        LocationDistrict currentLocationDistrict = locationDistrictRepo.findById(locationDistrictDTO.getId()).orElseThrow(
            () -> new ResourceNotFoundException(
                "locationDistrict with id [" + locationDistrictDTO.getId() + "] not found"
            )
        );
        currentLocationDistrict.setName(locationDistrictDTO.getName());
        locationDistrictRepo.save(currentLocationDistrict);
    }

    @Transactional
    public void delete(Integer id) {
        if (!locationDistrictRepo.existsById(id)) {
            throw new ResourceNotFoundException("locationDistrict with id [" + id + "] not found");
        }
        locationDistrictRepo.deleteById(id);
    }

    public LocationDistrictDTO getById(Integer id) {
        return locationDistrictRepo.findById(id).map(this::convert).orElseThrow(
            () -> new ResourceNotFoundException("locationDistrict with id [" + id + "] not found")
        );
    }

    public PageDTO<LocationDistrictDTO> searchService(SearchLocationDistrictDTO searchDTO) {
        Sort sortBy = Sort.by("id").descending();

        if (searchDTO.getSortedField() != null && !searchDTO.getSortedField().isEmpty()) {
            sortBy = Sort.by(searchDTO.getSortedField()).ascending();
        }

        PageRequest pageRequest = PageRequest.of(
            searchDTO.getCurrentPage(), searchDTO.getSize(), sortBy
        );

        String key = "%" + searchDTO.getKeyword() + "%";
        Integer c1 = (searchDTO.getLocationCityId() != null) ? searchDTO.getLocationCityId() : null;

        Page<LocationDistrict> page = locationDistrictRepo.findAll(pageRequest);

        if (c1 == null) {
            page = locationDistrictRepo.searchBy(key, pageRequest);
        }
        if (c1 != null) {
            page = locationDistrictRepo.searchByC1(key, c1, pageRequest);
        }

        return PageDTO.<LocationDistrictDTO>builder()
            .totalPage(page.getTotalPages())
            .totalElements(page.getTotalElements())
            .data(page.get().map(this::convert).toList())
            .build();
    }

    private LocationDistrictDTO convert(LocationDistrict locationDistrict) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(locationDistrict, LocationDistrictDTO.class);
    }
}

