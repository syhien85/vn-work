package root.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import root.entity.UserContact;

import java.util.Optional;

public interface UserContactRepo extends JpaRepository<UserContact, Long> {
    @Query(
        "SELECT uc FROM " +
            "UserContact uc " +
            "WHERE uc.user.accountId = :s"
    )
    Optional<UserContact> findUserContactsByUserAccountId(Long s);
}
