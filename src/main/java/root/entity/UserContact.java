package root.entity;

import jakarta.persistence.*;
import lombok.*;
import root.enums.GenderEnum;

import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class UserContact {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String email;
	private String phone; // copy from Account
	@Temporal(TemporalType.DATE)
	private Date birthdate;
	@Enumerated(EnumType.ORDINAL)
	private GenderEnum gender;
	private Boolean maritalStatus; // hôn nhân
	private Boolean nationality; // Quốc tịch

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Location location;

//	@OneToOne(mappedBy = "userContact", fetch = FetchType.EAGER)
	@OneToOne
	private User user;
}
