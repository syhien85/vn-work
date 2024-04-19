package root.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import root.dto.ResponseDTO;
import root.dto.UserAttachedCVDTO;
import root.service.UserAttachedCVService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user-attached-cv")
public class UserAttachedCVController {

    private final UserAttachedCVService userAttachedCVService;

    @Value("${upload.folder}")
    private String UPLOAD_FOLDER;

    @PostMapping
    public ResponseDTO<Void> create(
        @RequestParam("resumeId") Long resumeId,
        @RequestParam("file") MultipartFile file
    ) throws IOException {
        userAttachedCVService.create(resumeId, file);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @PutMapping
    public ResponseDTO<Void> update(
        @RequestParam("resumeId") Long resumeId,
        @RequestParam("file") MultipartFile file
    ) throws IOException {
        userAttachedCVService.update(resumeId, file);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @DeleteMapping
    public ResponseDTO<Void> delete(@RequestParam("id") Long id) {
        userAttachedCVService.delete(id);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @GetMapping
    public ResponseDTO<UserAttachedCVDTO> getById(@RequestParam("id") Long id) {
        return ResponseDTO.<UserAttachedCVDTO>builder()
            .status(200).msg("OK")
            .data(userAttachedCVService.getById(id))
            .build();
    }

    @GetMapping("/download")
    public void download(@RequestParam("filename") String filename, HttpServletResponse resp)
        throws IOException {
        File file = new File(UPLOAD_FOLDER + filename);
        Files.copy(file.toPath(), resp.getOutputStream());
    }
}
