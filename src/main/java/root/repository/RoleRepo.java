package root.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import root.entity.Role;

public interface RoleRepo extends JpaRepository<Role, Integer> {

    boolean existsByName(String name);
}
