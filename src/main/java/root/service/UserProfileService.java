package root.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import root.dto.IndustryDTO;
import root.dto.UserDTO;
import root.dto.UserProfileDTO;
import root.entity.Industry;
import root.entity.JobFunction;
import root.entity.UserProfile;
import root.exception.ResourceNotFoundException;
import root.repository.UserProfileRepo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserProfileService {

    private final UserProfileRepo userProfileRepo;

    @Value("${upload.folder}") private String UPLOAD_FOLDER;

    @Transactional
    public void create(UserProfileDTO userProfileDTO) {
        userProfileDTO.setFullName(userProfileDTO.getLastName() + " " + userProfileDTO.getFirstName());
        UserProfile userProfile = new ModelMapper().map(userProfileDTO, UserProfile.class);
        userProfileRepo.save(userProfile);
    }

    @Transactional
    public void update(UserProfileDTO userProfileDTO) {
        UserProfile currentUserProfile = userProfileRepo.findById(userProfileDTO.getId()).orElseThrow(
            () -> new ResourceNotFoundException(
                "userProfile with id [" + userProfileDTO.getId() + "] not found"
            )
        );
        currentUserProfile.setFirstName(userProfileDTO.getFirstName());
        currentUserProfile.setLastName(userProfileDTO.getLastName());
        currentUserProfile.setFullName(userProfileDTO.getLastName() + " " + userProfileDTO.getFirstName());
//        currentUserProfile.setAvatarUrl(userProfileDTO.getAvatarUrl());

        currentUserProfile.setJobTitle(userProfileDTO.getJobTitle());
        currentUserProfile.setYearsExperience(userProfileDTO.getYearsExperience());
        currentUserProfile.setHighestDegree(userProfileDTO.getHighestDegree());
        currentUserProfile.setCurrentSalary(userProfileDTO.getCurrentSalary());
        currentUserProfile.setJobLevel(userProfileDTO.getJobLevel());

        currentUserProfile.setJobFunction(
            new ModelMapper().map(userProfileDTO.getJobFunction(), JobFunction.class)
        );

        currentUserProfile.setIndustries(
            userProfileDTO.getIndustries().stream().map(
                industryDTO -> new ModelMapper().map(industryDTO, Industry.class)
            ).toList()
        );
        userProfileRepo.save(currentUserProfile);
    }

    @Transactional
    public void delete(Long id) {
        if (!userProfileRepo.existsById(id)) {
            throw new ResourceNotFoundException("userProfile with id [" + id + "] not found");
        }
        userProfileRepo.deleteById(id);
    }

    public UserProfileDTO getById(Long id) {
        return userProfileRepo.findById(id).map(this::convert).orElseThrow(
            () -> new ResourceNotFoundException("userProfile with id [" + id + "] not found")
        );
    }

    @Transactional
    public void updateAvatar(Long id, MultipartFile file) {
        UserProfile currentUserProfile = userProfileRepo.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("userProfile with id [" + id + "] not found")
        );

        currentUserProfile.setAvatarUrl(uploadFilePath(file));
        userProfileRepo.save(currentUserProfile);
    }

    private UserProfileDTO convert(UserProfile userProfile) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(userProfile, UserProfileDTO.class);
    }

    private String uploadFilePath(MultipartFile fileUpload){
        String newFilename = null;

        if (fileUpload != null && !fileUpload.isEmpty()) {
            if (new File(UPLOAD_FOLDER).exists()) {
                new File(UPLOAD_FOLDER).mkdir();
            }

            String filename = fileUpload.getOriginalFilename();

            if (filename != null) {
                String extension = filename.substring((filename.lastIndexOf(".")));
                String fileNameWithoutExtension = filename.substring(0, filename.lastIndexOf("."));
                newFilename = fileNameWithoutExtension + "-" + UUID.randomUUID() + extension;
                File newFile = new File(UPLOAD_FOLDER + newFilename);
                try {
                    fileUpload.transferTo(newFile);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return newFilename;
    }
}

