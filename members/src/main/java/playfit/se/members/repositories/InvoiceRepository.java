package playfit.se.members.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import playfit.se.members.entities.InvoiceEntity;
import playfit.se.members.entities.SubscriptionEntity;
import playfit.se.members.entities.UserEntity;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<InvoiceEntity, Long> {
    List<InvoiceEntity> findByUserEntity(UserEntity user);
    List<InvoiceEntity> findBySubscriptionEntity(SubscriptionEntity subscription);

}
