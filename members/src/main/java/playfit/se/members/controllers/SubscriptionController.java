package playfit.se.members.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import playfit.se.members.DTOs.SubscriptionCreationRequestDTO;
import playfit.se.members.entities.SubscriptionEntity;
import playfit.se.members.services.SubscriptionService;

import java.util.List;

@RequestMapping("/api/v1/Subscriptions")
@RestController
@RequiredArgsConstructor
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @PostMapping("/create-subscription")
    public ResponseEntity<SubscriptionEntity> createSubscription(@RequestBody SubscriptionCreationRequestDTO request) {
        SubscriptionEntity subscription = subscriptionService.createSubscription(
                request.getType(), request.getPricingId(), request.getDurationInMonths()
        );
        return new ResponseEntity<>(subscription, HttpStatus.CREATED);
    }
    @GetMapping("/all-subscriptions")
    public ResponseEntity<List<SubscriptionEntity>> getAllSubscriptions() {
        List<SubscriptionEntity> subscriptions = subscriptionService.getAllSubscriptions();
        return ResponseEntity.ok(subscriptions);
    }

    @PostMapping("/{userId}/assign-subscription/{subscriptionId}")
    public ResponseEntity<?> assignSubscriptionToUser(@PathVariable Long userId, @PathVariable Long subscriptionId) {
        subscriptionService.assignSubscriptionToUser(userId, subscriptionId);
        return ResponseEntity.ok("Subscription assigned successfully");
    }
}

