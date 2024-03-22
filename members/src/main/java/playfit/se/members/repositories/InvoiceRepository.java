package playfit.se.members.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import playfit.se.members.entities.InvoiceEntity;
import playfit.se.members.entities.SubscriptionEntity;
import playfit.se.members.entities.UserEntity;

import java.util.List;
@Repository
public interface InvoiceRepository extends JpaRepository<InvoiceEntity, Long> {
    List<InvoiceEntity> findByUserEntity(UserEntity user);
    List<InvoiceEntity> findBySubscriptionEntity(SubscriptionEntity subscription);

}
