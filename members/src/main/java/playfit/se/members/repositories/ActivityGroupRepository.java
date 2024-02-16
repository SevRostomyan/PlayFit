package playfit.se.members.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import playfit.se.members.entities.ActivityGroupEntity;

import java.util.Optional;

@Repository
public interface ActivityGroupRepository extends JpaRepository<ActivityGroupEntity, Long> {
    Optional <ActivityGroupEntity> findByActivityName(String name);
}
