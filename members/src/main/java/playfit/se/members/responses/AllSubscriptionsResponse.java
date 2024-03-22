package playfit.se.members.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import playfit.se.members.entities.SubscriptionEntity;

import java.util.List;

@Data
@AllArgsConstructor
public class AllSubscriptionsResponse {
    private boolean success;
    private String message;
    private List<SubscriptionEntity> subscriptions;
}
