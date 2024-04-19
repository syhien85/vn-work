package root.dto;

import lombok.*;

@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO extends TimeAuditableDTO {
	private AccountDTO account;
}
