package root.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import root.dto.RoleDTO;
import root.entity.Role;
import root.exception.DuplicateResourceException;
import root.exception.ResourceNotFoundException;
import root.repository.RoleRepo;

@RequiredArgsConstructor
@Service
public class RoleService {

    private final RoleRepo roleRepo;

    @Transactional
    public void create(RoleDTO roleDTO) {
        if (roleRepo.existsByName(roleDTO.getName())) {
            throw new DuplicateResourceException(
                ("role with name [" + roleDTO.getName() + "] already taken")
            );
        }
        Role role = new ModelMapper().map(roleDTO, Role.class);
        roleRepo.save(role);
    }

    @Transactional
    public void update(RoleDTO roleDTO) {
        Role currentRole = roleRepo.findById(roleDTO.getId()).orElseThrow(
            () -> new ResourceNotFoundException(
                "role with id [" + roleDTO.getId() + "] not found"
            )
        );
        currentRole.setName(roleDTO.getName());
        roleRepo.save(currentRole);
    }

    @Transactional
    public void delete(Integer id) {
        if (!roleRepo.existsById(id)) {
            throw new ResourceNotFoundException("role with id [" + id + "] not found");
        }
        roleRepo.deleteById(id);
    }

    public RoleDTO getById(Integer id) {
        return roleRepo.findById(id).map(this::convert).orElseThrow(
            () -> new ResourceNotFoundException("role with id [" + id + "] not found")
        );
    }

    private RoleDTO convert(Role role) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(role, RoleDTO.class);
    }
}

