package root.dto;

import lombok.*;
import root.entity.Role;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AccountDTO {
	private Long id;
	private String username;
	private String password;
	private String email;

	private List<Role> roles;
}
