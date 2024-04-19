package root.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String username;
	private String password;
	@Column(unique = true)
	private String email;

	@ManyToMany(fetch = FetchType.EAGER)
	private List<Role> roles;

	/*@OneToOne
	private User user;*/

	/*@OneToOne
	private Company company;*/
}
