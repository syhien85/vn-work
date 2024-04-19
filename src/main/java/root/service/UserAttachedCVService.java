package root.service;

import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import root.dto.UserAttachedCVDTO;
import root.entity.Resume;
import root.entity.UserAttachedCV;
import root.exception.ResourceNotFoundException;
import root.repository.ResumeRepo;
import root.repository.UserAttachedCVRepo;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserAttachedCVService {

    private final UserAttachedCVRepo userAttachedCVRepo;
    private final ResumeRepo resumeRepo;
    @Value("${upload.folder}")
    private String UPLOAD_FOLDER;

    @Transactional
    public void create(Long id, MultipartFile file) {
        Resume currentResume = resumeRepo.findById(id).orElseThrow(
            () -> new ResourceNotFoundException(
                "resume with id [" + id + "] not found"
            )
        );

        UserAttachedCV userAttachedCV = UserAttachedCV.builder()
            .attachedUrl(uploadFilePath(file))
            .resume(currentResume)
            .isAttached(true)
            .build();
        userAttachedCVRepo.save(userAttachedCV);
    }

    @Transactional
    public void update(Long id, MultipartFile file) {
        Resume currentResume = resumeRepo.findById(id).orElseThrow(
            () -> new ResourceNotFoundException(
                "resume with id [" + id + "] not found"
            )
        );
        UserAttachedCV userAttachedCV = UserAttachedCV.builder()
            .attachedUrl(uploadFilePath(file))
            .resume(currentResume)
            .build();
        userAttachedCVRepo.save(userAttachedCV);
    }

    @Transactional
    public void delete(Long id) {
        if (!userAttachedCVRepo.existsById(id)) {
            throw new ResourceNotFoundException("userAttachedCV with id [" + id + "] not found");
        }
        userAttachedCVRepo.deleteById(id);
    }

    public UserAttachedCVDTO getById(Long id) {
        return userAttachedCVRepo.findById(id).map(this::convert).orElseThrow(
            () -> new ResourceNotFoundException("userAttachedCV with id [" + id + "] not found")
        );
    }

    private UserAttachedCVDTO convert(UserAttachedCV userAttachedCV) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(userAttachedCV, UserAttachedCVDTO.class);
    }

    private String uploadFilePath(MultipartFile fileUpload) throws IOException {
        String newFilename = null;

        if (fileUpload != null && !fileUpload.isEmpty()) {
            if (new File(UPLOAD_FOLDER).exists()) {
                new File(UPLOAD_FOLDER).mkdir();
            }

            String filename = fileUpload.getOriginalFilename();

            if (filename != null) {
                String extension = filename.substring((filename.lastIndexOf(".")));
                newFilename = UUID.randomUUID() + extension;
                File newFile = new File(UPLOAD_FOLDER + newFilename);
                try {
                    fileUpload.transferTo(newFile);
                } catch (IOException | java.io.IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return newFilename;
    }
}

