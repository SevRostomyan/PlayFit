package playfit.se.members.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import playfit.se.members.entities.SessionEntity;

@Repository
public interface SessionRepository extends JpaRepository<SessionEntity, Long> {
}
