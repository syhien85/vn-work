package root.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import root.entity.WorkingPreference;

public interface WorkingPreferenceRepo extends JpaRepository<WorkingPreference, Long> {
}
