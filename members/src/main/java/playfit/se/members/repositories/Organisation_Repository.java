package playfit.se.members.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import playfit.se.members.entity.Organisation_Club_Entity;

@Repository
public interface Organisation_Repository extends JpaRepository<Organisation_Club_Entity, Long> {
}
