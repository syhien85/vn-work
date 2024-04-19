package root.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import root.enums.CompanySizeEnum;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CompanyInfoDTO {
    private Long id;
    private String companyName;
    private String companyLogoUrl;
    @JsonIgnore
    private MultipartFile file;
    private String companyProfile;
    private CompanySizeEnum companySize;
    private String contactName;
    private String website;
    private String contactEmail;
    //    private String jobImageURLs;
    private String address;
    private String whyWorkWithUs;
}
