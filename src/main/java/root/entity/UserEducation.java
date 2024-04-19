package root.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import root.enums.HighestEducationEnum;

import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(
    uniqueConstraints = {
        @UniqueConstraint(
            name = "UniqueSchoolNameIdAndUserId",
            columnNames = {"school_name", "resume_id"}
        )
    }
)
public class UserEducation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.ORDINAL)
    private HighestEducationEnum highestDegree; // bằng cấp cao nhất

//    private Long schoolId;
    private String schoolName;
    private String major;
    private String description;
//    private Integer countryId;

    @Temporal(TemporalType.DATE)
    private Date startDate; // từ tháng
    @Temporal(TemporalType.DATE)
    private Date endDate; // đến tháng

    @ManyToOne
    private Resume resume;
}
