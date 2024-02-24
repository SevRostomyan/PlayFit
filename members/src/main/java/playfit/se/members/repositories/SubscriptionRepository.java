package playfit.se.members.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import playfit.se.members.entities.SubscriptionEntity;

public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, Long> {
}
