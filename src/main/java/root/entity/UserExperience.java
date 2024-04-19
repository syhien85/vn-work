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
@Table(
    uniqueConstraints = {
        @UniqueConstraint(
            name = "UniqueCompanyIdAndUserId",
            columnNames = {"company", "resume_id"}
        )
    }
) // mỗi cặp company và user_id là duy nhất
public class UserExperience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String jobTitle; // chức danh
    private String company; // công ty

    @Temporal(TemporalType.DATE)
    private Date startDate; // từ tháng
    @Temporal(TemporalType.DATE)
    private Date endDate; // đến tháng

    private String description; // mô tả

    @ManyToOne
    private Resume resume;
}
