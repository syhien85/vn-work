package root.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import root.entity.UserProfile;

public interface UserProfileRepo extends JpaRepository<UserProfile, Long> {
}
