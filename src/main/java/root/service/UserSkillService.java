package root.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import root.dto.UserSkillDTO;
import root.entity.UserSkill;
import root.exception.ResourceNotFoundException;
import root.repository.SkillRepo;
import root.repository.UserSkillRepo;

@RequiredArgsConstructor
@Service
public class UserSkillService {

    private final UserSkillRepo userSkillRepo;
    private final SkillRepo skillRepo;

    @Transactional
    public void create(UserSkillDTO userSkillDTO) {

        UserSkill userSkill = new ModelMapper().map(userSkillDTO, UserSkill.class);

        Long skillId = userSkill.getSkill().getId();
        if ( skillId == null || !skillRepo.existsById(skillId) ) {
            if (
                userSkill.getSkill().getName() != null &&
                    !skillRepo.existsByName(userSkill.getSkill().getName())
            ) {
                // if skill by name not exist, create skill and set skill.setSkill
                skillRepo.save(userSkill.getSkill());
                userSkill.setSkill(userSkill.getSkill());
            } else if (
                userSkill.getSkill().getName() != null &&
                    skillRepo.existsByName(userSkill.getSkill().getName())
            ) {
                // if skill name exist, skill.setSkill
                userSkill.setSkill(skillRepo.findByName(userSkill.getSkill().getName()).get());
            }
        } else if (skillRepo.existsById(skillId)) {
            // if skill id exist, skill.setSkill
            userSkill.setSkill(skillRepo.findById(skillId).get());
        }

        userSkillRepo.save(userSkill);
    }

    @Transactional
    public void update(UserSkillDTO userSkillDTO) {
        UserSkill currentUserSkill = userSkillRepo.findById(userSkillDTO.getId()).orElseThrow(
            () -> new ResourceNotFoundException(
                "userSkill with id [" + userSkillDTO.getId() + "] not found"
            )
        );
        currentUserSkill.setLevel(userSkillDTO.getLevel());
        userSkillRepo.save(currentUserSkill);
    }

    @Transactional
    public void delete(Long id) {
        if (!userSkillRepo.existsById(id)) {
            throw new ResourceNotFoundException("userSkill with id [" + id + "] not found");
        }
        userSkillRepo.deleteById(id);
    }

    public UserSkillDTO getById(Long id) {
        return userSkillRepo.findById(id).map(this::convert).orElseThrow(
            () -> new ResourceNotFoundException("userSkill with id [" + id + "] not found")
        );
    }

    private UserSkillDTO convert(UserSkill userSkill) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(userSkill, UserSkillDTO.class);
    }
}

