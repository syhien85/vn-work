package root.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class LocationCity {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @OneToMany(
        mappedBy = "locationCity",
        cascade = CascadeType.ALL,
        fetch = FetchType.EAGER
    )
    private List<LocationDistrict> locationDistricts;
}
