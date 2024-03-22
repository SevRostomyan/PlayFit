package playfit.se.members.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import playfit.se.members.entities.SubscriptionEntity;
@Repository
public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, Long> {
}
