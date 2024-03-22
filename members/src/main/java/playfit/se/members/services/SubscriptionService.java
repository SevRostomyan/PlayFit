package playfit.se.members.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import playfit.se.members.entities.PricingEntity;
import playfit.se.members.entities.SubscriptionEntity;
import playfit.se.members.entities.UserEntity;
import playfit.se.members.repositories.PricingRepository;
import playfit.se.members.repositories.SubscriptionRepository;
import playfit.se.members.enums.SubscriptionType;
import playfit.se.members.repositories.UserEntityRepository;
import playfit.se.members.responses.AllSubscriptionsResponse;
import playfit.se.members.responses.AssignSubscriptionResponse;
import playfit.se.members.responses.CreateSubscriptionResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final PricingRepository pricingRepository;
    private final UserEntityRepository userEntityRepository;

    public CreateSubscriptionResponse createSubscription(SubscriptionType type, Long pricingId, Integer durationInMonths) {
        try {
            PricingEntity pricing = pricingRepository.findById(pricingId)
                    .orElseThrow(() -> new IllegalArgumentException("Pricing not found"));

            SubscriptionEntity subscription = new SubscriptionEntity();
            subscription.setType(type);
            subscription.setPricing(pricing);
            subscription.setDurationInMonths(durationInMonths);

            SubscriptionEntity savedSubscription = subscriptionRepository.save(subscription);
            return new CreateSubscriptionResponse(true, "Subscription created successfully", savedSubscription);
        } catch (Exception e) {
            return new CreateSubscriptionResponse(false, "Failed to create subscription: " + e.getMessage(), null);
        }
    }

    public AllSubscriptionsResponse getAllSubscriptions() {
        try {
            List<SubscriptionEntity> subscriptions = subscriptionRepository.findAll();
            return new AllSubscriptionsResponse(true, "Subscriptions fetched successfully", subscriptions);
        } catch (Exception e) {
            return new AllSubscriptionsResponse(false, "Failed to fetch subscriptions: " + e.getMessage(), null);
        }
    }

    @Transactional
    public AssignSubscriptionResponse assignSubscriptionToUser(Long userId, Long subscriptionId) {
        AssignSubscriptionResponse response = new AssignSubscriptionResponse();
        try {
            UserEntity user = userEntityRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));

            SubscriptionEntity subscription = subscriptionRepository.findById(subscriptionId)
                    .orElseThrow(() -> new IllegalArgumentException("Subscription not found"));

            user.setSubscription(subscription);
            userEntityRepository.save(user);

            response.setSuccess(true);
            response.setMessage("Subscription assigned successfully");
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage("Failed to assign subscription: " + e.getMessage());
        }
        return response;
    }
}

