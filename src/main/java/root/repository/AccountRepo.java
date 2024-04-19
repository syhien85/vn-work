package root.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import root.entity.Account;

import java.util.List;
import java.util.Optional;

public interface AccountRepo extends JpaRepository<Account, Long> {
    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    Optional<Account> findByUsername(String username);

    @Query("SELECT a FROM Account a WHERE a.username LIKE :s")
    Page<Account> searchByUsername(@Param("s") String s, Pageable pageable);

    /*@Query(
        "SELECT a " +
            "FROM Account a " +
            "WHERE DAY(a.birthdate) = :date AND MONTH(a.birthdate) = :month"
    )
    List<Account> searchByBirthdate(@Param("date") int date, @Param("month") int month);*/

    @Query("SELECT a FROM Account a WHERE a.email LIKE :email")
    Optional<Account> findByEmail(@Param("email") String s);

    @Query(
        "SELECT a " +
            "FROM Account a " +
            "WHERE a.username LIKE :usernameOrEmail OR a.email LIKE :usernameOrEmail"
    )
    Optional<Account> findByUsernameOrEmail(@Param("usernameOrEmail") String s);
}
