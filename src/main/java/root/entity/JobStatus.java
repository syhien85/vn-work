package root.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class JobStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean isActive;

    private boolean isApproved;

    @Temporal(TemporalType.TIMESTAMP)
    private Date approvedOn;
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiredOn;
//    private Integer durationDays;
}
