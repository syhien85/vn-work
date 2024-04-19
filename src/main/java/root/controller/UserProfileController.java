package root.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import root.dto.ResponseDTO;
import root.dto.UserProfileDTO;
import root.service.UserProfileService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user-profile")
public class UserProfileController {

    private final UserProfileService userProfileService;

    @Value("${upload.folder}") private String UPLOAD_FOLDER;

    @PostMapping
    public ResponseDTO<Void> create(@RequestBody @Valid UserProfileDTO userProfileDTO) {
        userProfileService.create(userProfileDTO);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @PutMapping
    public ResponseDTO<Void> update(@RequestBody UserProfileDTO userProfileDTO) {
        userProfileService.update(userProfileDTO);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @DeleteMapping
    public ResponseDTO<Void> delete(@RequestParam("id") Long id) {
        userProfileService.delete(id);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @GetMapping
    public ResponseDTO<UserProfileDTO> getById(@RequestParam("id") Long id) {
        return ResponseDTO.<UserProfileDTO>builder()
            .status(200).msg("OK")
            .data(userProfileService.getById(id))
            .build();
    }

    @PutMapping("/update-avatar")
    public ResponseDTO<Void> updateAvatar(
        @RequestParam("id") Long id,
        @RequestParam("file") MultipartFile file
    ) {
        userProfileService.updateAvatar(id, file);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @GetMapping("/download")
    public void download(@RequestParam("filename") String filename, HttpServletResponse resp)
        throws IOException {
        File file = new File(UPLOAD_FOLDER + filename);
        Files.copy(file.toPath(), resp.getOutputStream());
    }
}
