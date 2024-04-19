package root.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import root.enums.GenderEnum;

import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserContactDTO {
    private Long id;
    private String email;
    private String phone;
    @DateTimeFormat(pattern = "dd/MM/yyyy") //
    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "GMT+7")
    private Date birthdate;
    private GenderEnum gender;
    private Boolean maritalStatus; // hôn nhân
    private Boolean nationality; // Quốc tịch

    private LocationDTO location;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UserDTO user;
}
