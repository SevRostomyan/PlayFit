package playfit.se.members.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import playfit.se.members.entities.PricingEntity;

import java.util.Optional;

@Repository
public interface PricingRepository extends JpaRepository<PricingEntity, Long> {

    Optional<PricingEntity> findByName(String freeSession);
}
