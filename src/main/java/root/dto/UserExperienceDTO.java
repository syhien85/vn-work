package root.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserExperienceDTO {
    private Long id;
    private String jobTitle; // chức danh
    private String company; // công ty ??

    @DateTimeFormat(pattern = "dd/MM/yyyy") //
    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "GMT+7")
    private Date startDate; // từ tháng
    @DateTimeFormat(pattern = "dd/MM/yyyy") //
    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "GMT+7")
    private Date endDate; // đến tháng

//    "isCurrent": 0,
    private String description; // mô tả

    @JsonBackReference
    private ResumeDTO resume;
}
