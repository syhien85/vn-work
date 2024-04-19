package root.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Company extends TimeAuditable {
	@Id
	private Long accountId;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@PrimaryKeyJoinColumn
	@MapsId
	private Account account;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private CompanyInfo companyInfo;

// CompanyStats // Thống kê
//	private Long followerCount;
//	private Long viewCount;
//	private Long isFollowed;
//	private Integer onlineJobCount;
}
