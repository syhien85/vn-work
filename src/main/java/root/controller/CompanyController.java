package root.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.web.bind.annotation.*;
import root.dto.*;
import root.service.CompanyService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/company")
public class CompanyController {

    private final CompanyService companyService;

    @Value("${upload.folder}")
    private String UPLOAD_FOLDER;

    @CacheEvict(cacheNames = "company-search", allEntries = true)
    @PostMapping
    public ResponseDTO<Void> create(@RequestBody @Valid CompanyDTO companyDTO) {
        companyService.create(companyDTO);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @Caching(evict = {
        @CacheEvict(cacheNames = "company-search", allEntries = true),
        @CacheEvict(cacheNames = "company", key = "#companyDTO.account.id") // khi update delete cache
    })
    @PutMapping
    public ResponseDTO<Void> update(@RequestBody CompanyDTO companyDTO) {
        companyService.update(companyDTO);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @CacheEvict(cacheNames = "company", key = "#id")
    @DeleteMapping
    public ResponseDTO<Void> delete(@RequestParam("id") Long id) {
        companyService.delete(id);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @Cacheable(cacheNames = "company", key = "#id", unless = "#result == null")
    @GetMapping
    public ResponseDTO<CompanyDTO> getById(@RequestParam("id") Long id) {
        return ResponseDTO.<CompanyDTO>builder()
            .status(200).msg("OK")
            .data(companyService.getById(id))
            .build();
    }

    @Cacheable(cacheNames = "company-search")
    @PostMapping("/search")
    public ResponseDTO<PageDTO<CompanyDTO>> search(@RequestBody SearchDTO searchDTO) {
        return ResponseDTO.<PageDTO<CompanyDTO>>builder().status(200).msg("OK")
            .data(companyService.searchService(searchDTO))
            .build();
    }

    @PutMapping("/update-company-logo")
    public ResponseDTO<Void> updateCompanyLogo(@ModelAttribute("Company") CompanyDTO companyDTO)
        throws IOException {

        if (!companyDTO.getCompanyInfo().getFile().isEmpty()) {
            if (new File(UPLOAD_FOLDER).exists()) {
                new File(UPLOAD_FOLDER).mkdir();
            }
            String filename = companyDTO.getCompanyInfo().getFile().getOriginalFilename();
            // lấy định dạng file
            String extension = filename.substring((filename.lastIndexOf(".")));

            String newFilename = UUID.randomUUID().toString() + extension;
            File newFile = new File(UPLOAD_FOLDER + newFilename);
            companyDTO.getCompanyInfo().getFile().transferTo(newFile);

            companyDTO.getCompanyInfo().setCompanyLogoUrl(newFilename);
        }

        companyService.updateCompanyLogo(companyDTO);

        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @GetMapping("/download")
    public void download(@RequestParam("filename") String filename, HttpServletResponse resp)
        throws IOException {
        File file = new File(UPLOAD_FOLDER + filename);
        Files.copy(file.toPath(), resp.getOutputStream());
    }
}
