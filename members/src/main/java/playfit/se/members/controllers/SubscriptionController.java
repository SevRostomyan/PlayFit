package playfit.se.members.controllers;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import playfit.se.members.DTOs.SubscriptionCreationRequestDTO;
import playfit.se.members.entities.SubscriptionEntity;
import playfit.se.members.responses.AllSubscriptionsResponse;
import playfit.se.members.responses.AssignSubscriptionResponse;
import playfit.se.members.responses.CreateSubscriptionResponse;
import playfit.se.members.services.SubscriptionService;

import java.util.List;

@RequestMapping("/api/v1/Subscriptions")
@RestController
@RequiredArgsConstructor
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @PostMapping("/create-subscription")
    public ResponseEntity<CreateSubscriptionResponse> createSubscription(@RequestBody SubscriptionCreationRequestDTO request) {
        CreateSubscriptionResponse response = subscriptionService.createSubscription(
                request.getType(), request.getPricingId(), request.getDurationInMonths()
        );
        return response.isSuccess() ? ResponseEntity.ok(response) : ResponseEntity.badRequest().body(response);
    }

    @GetMapping("/all-subscriptions")
    public ResponseEntity<AllSubscriptionsResponse> getAllSubscriptions() {
        AllSubscriptionsResponse response = subscriptionService.getAllSubscriptions();
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/{userId}/assign-subscription/{subscriptionId}")
    public ResponseEntity<AssignSubscriptionResponse> assignSubscriptionToUser(@PathVariable Long userId, @PathVariable Long subscriptionId) {
        AssignSubscriptionResponse response = subscriptionService.assignSubscriptionToUser(userId, subscriptionId);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }
}

