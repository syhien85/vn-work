package root.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import root.dto.JobSkillDTO;
import root.entity.JobSkill;
import root.entity.Skill;
import root.exception.ResourceNotFoundException;
import root.repository.SkillRepo;
import root.repository.JobSkillRepo;

@RequiredArgsConstructor
@Service
public class JobSkillService {

    private final JobSkillRepo jobSkillRepo;
    private final SkillRepo skillRepo;

    @Transactional
    public void create(JobSkillDTO jobSkillDTO) {

        JobSkill jobSkill = new ModelMapper().map(jobSkillDTO, JobSkill.class);

        Long skillId = jobSkill.getSkill().getId();
        if ( skillId == null || !skillRepo.existsById(skillId) ) {
            if (
                jobSkill.getSkill().getName() != null &&
                    !skillRepo.existsByName(jobSkill.getSkill().getName())
            ) {
                // if skill by name not exist, create skill and set skill.setSkill
                skillRepo.save(jobSkill.getSkill());
                jobSkill.setSkill(jobSkill.getSkill());
            } else if (
                jobSkill.getSkill().getName() != null &&
                    skillRepo.existsByName(jobSkill.getSkill().getName())
            ) {
                // if skill name exist, skill.setSkill
                jobSkill.setSkill(skillRepo.findByName(jobSkill.getSkill().getName()).get());
            }
        } else if (skillRepo.existsById(skillId)) {
            // if skill id exist, skill.setSkill
            jobSkill.setSkill(skillRepo.findById(skillId).get());
        }

        jobSkillRepo.save(jobSkill);
    }

    @Transactional
    public void update(JobSkillDTO jobSkillDTO) {
        JobSkill currentJobSkill = jobSkillRepo.findById(jobSkillDTO.getId()).orElseThrow(
            () -> new ResourceNotFoundException(
                "jobSkill with id [" + jobSkillDTO.getId() + "] not found"
            )
        );
        currentJobSkill.setSkill(
            new ModelMapper().map(jobSkillDTO.getSkill(), Skill.class)
        );
        jobSkillRepo.save(currentJobSkill);
    }

    @Transactional
    public void delete(Long id) {
        if (!jobSkillRepo.existsById(id)) {
            throw new ResourceNotFoundException("jobSkill with id [" + id + "] not found");
        }
        jobSkillRepo.deleteById(id);
    }

    public JobSkillDTO getById(Long id) {
        return jobSkillRepo.findById(id).map(this::convert).orElseThrow(
            () -> new ResourceNotFoundException("jobSkill with id [" + id + "] not found")
        );
    }

    private JobSkillDTO convert(JobSkill jobSkill) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(jobSkill, JobSkillDTO.class);
    }
}

