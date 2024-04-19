package root.dto;

import lombok.*;

@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CompanyDTO extends TimeAuditableDTO {

	private AccountDTO account;

	private CompanyInfoDTO companyInfo;

// CompanyStats // Thống kê
//	private Long followerCount;
//	private Long viewCount;
//	private Long isFollowed;
//	private Integer onlineJobCount;
}
