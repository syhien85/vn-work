package root.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import root.enums.JobLevelEnum;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class WorkingPreference { // Công việc mong muốn
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String jobTitle; // Chức danh
    @Enumerated(EnumType.ORDINAL)
    private JobLevelEnum jobLevel; // cấp bậc (nhân viên, leader, ..)
    private String benefits; // phúc lợi
    private Double salary; // mức lương mong muốn
    private Boolean isRelocate; // có thể thay đổi nơi làm việc

    @ManyToOne
    private LocationCity locationCity; // nơi làm việc (tỉnh, thành phố)

    @ManyToOne
    private Language language;

    @ManyToOne
    private JobFunction jobFunction;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Industry> industries; // lĩnh vực (CNTT)

    @OneToOne
    private Resume resume;
}
