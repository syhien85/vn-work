package root.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import root.enums.PermissionMethodEnum;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
    uniqueConstraints = {
        @UniqueConstraint(
            name = "UniquePathAndMethod",
            columnNames = {"path", "method"}
        )
    }
)
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String path;
    @Enumerated(EnumType.ORDINAL)
    private PermissionMethodEnum method;
    private boolean visibility;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
        name = "permission_roles",
        joinColumns = @JoinColumn(name = "permission_id")
    )
    @Column(name = "role")
    private List<String> roles;
}
