package root.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class User extends TimeAuditable {
	@Id
	private Long accountId;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@PrimaryKeyJoinColumn
	@MapsId
	private Account account;
}
