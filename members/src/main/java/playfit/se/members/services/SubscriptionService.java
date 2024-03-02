package playfit.se.members.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import playfit.se.members.entities.PricingEntity;
import playfit.se.members.entities.SubscriptionEntity;
import playfit.se.members.entities.UserEntity;
import playfit.se.members.repositories.PricingRepository;
import playfit.se.members.repositories.SubscriptionRepository;
import playfit.se.members.enums.SubscriptionType;
import playfit.se.members.repositories.UserEntityRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final PricingRepository pricingRepository;
    private final UserEntityRepository userEntityRepository;

    public SubscriptionEntity createSubscription(SubscriptionType  type, Long pricingId, Integer durationInMonths) {
        PricingEntity pricing = pricingRepository.findById(pricingId)
                .orElseThrow(() -> new IllegalArgumentException("Pricing not found"));

        SubscriptionEntity subscription = new SubscriptionEntity();
        subscription.setType(type); // Use the enum type here
        subscription.setPricing(pricing); // Link to the selected PricingEntity
        subscription.setDurationInMonths(durationInMonths);
        return subscriptionRepository.save(subscription);
    }

    public List<SubscriptionEntity> getAllSubscriptions() {
        return subscriptionRepository.findAll();
    }

    public void assignSubscriptionToUser(Long userId, Long subscriptionId) {
        UserEntity user = userEntityRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        SubscriptionEntity subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new IllegalArgumentException("Subscription not found"));

        user.getSubscriptions().add(subscription);
        userEntityRepository.save(user);
    }
}
