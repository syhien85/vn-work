package root.dto;

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
public class JobStatusDTO {
    private Long id;

    private boolean isActive;

    private boolean isApproved;

    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss") //
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "GMT+7")
    private Date approvedOn;
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss") //
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "GMT+7")
    private Date expiredOn;
//    private Integer durationDays;
}
