package root.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import root.enums.HighestEducationEnum;

import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserEducationDTO {
    private Long id;
    private HighestEducationEnum highestDegree; // bằng cấp cao nhất
//    private Long schoolId;
    private String schoolName;
    private String major;
    private String description;

    @DateTimeFormat(pattern = "dd/MM/yyyy") //
    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "GMT+7")
    private Date startDate; // từ tháng
    @DateTimeFormat(pattern = "dd/MM/yyyy") //
    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "GMT+7")
    private Date endDate; // đến tháng

    @JsonBackReference
    private ResumeDTO resume;
}
