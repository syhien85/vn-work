package root.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import root.entity.User;

public interface UserRepo extends JpaRepository<User, Long> {

    @Query(
        "SELECT u " +
            "FROM User u " +
            "WHERE u.account.username LIKE :s"
    )
    Page<User> searchByName(@Param("s") String s, Pageable pageable);
}
