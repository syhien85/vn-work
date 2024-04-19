package root.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import root.enums.CompanySizeEnum;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class CompanyInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String companyName;
    private String companyLogoUrl;
    private String companyProfile;

    @Enumerated(EnumType.ORDINAL)
    private CompanySizeEnum companySize;

    private String contactName;
    private String website;
    private String contactEmail;
    //    private String jobImageURLs;
    private String address;
    private String whyWorkWithUs;

    /*@OneToMany(mappedBy = "companyInfo")
    private List<Location> workingLocation;*/
}
