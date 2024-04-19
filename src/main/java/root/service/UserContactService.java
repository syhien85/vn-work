package root.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import root.dto.UserContactDTO;
import root.entity.Location;
import root.entity.UserContact;
import root.exception.ResourceNotFoundException;
import root.repository.UserContactRepo;

@RequiredArgsConstructor
@Service
public class UserContactService {

    private final UserContactRepo userContactRepo;

    @Transactional
    public void create(UserContactDTO userContactDTO) {
        UserContact userContact = new ModelMapper().map(userContactDTO, UserContact.class);
        userContactRepo.save(userContact);
    }

    @Transactional
    public void update(UserContactDTO userContactDTO) {
        UserContact currentUserContact = userContactRepo.findById(userContactDTO.getId()).orElseThrow(
            () -> new ResourceNotFoundException(
                "userContact with id [" + userContactDTO.getId() + "] not found"
            )
        );
        currentUserContact.setEmail(userContactDTO.getEmail());
        currentUserContact.setPhone(userContactDTO.getPhone());
        currentUserContact.setBirthdate(userContactDTO.getBirthdate());
        currentUserContact.setGender(userContactDTO.getGender());
        currentUserContact.setMaritalStatus(userContactDTO.getMaritalStatus());
        currentUserContact.setNationality(userContactDTO.getNationality());
        currentUserContact.setLocation(
            new ModelMapper().map(userContactDTO.getLocation(), Location.class)
        );
        userContactRepo.save(currentUserContact);
    }

    @Transactional
    public void delete(Long id) {
        if (!userContactRepo.existsById(id)) {
            throw new ResourceNotFoundException("userContact with id [" + id + "] not found");
        }
        userContactRepo.deleteById(id);
    }

    public UserContactDTO getById(Long id) {
        return userContactRepo.findById(id).map(this::convert).orElseThrow(
            () -> new ResourceNotFoundException("userContact with id [" + id + "] not found")
        );
    }

    private UserContactDTO convert(UserContact userContact) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(userContact, UserContactDTO.class);
    }
}

