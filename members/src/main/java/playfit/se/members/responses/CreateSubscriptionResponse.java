package playfit.se.members.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import playfit.se.members.entities.SubscriptionEntity;

@Data
@AllArgsConstructor
public class CreateSubscriptionResponse {
    private boolean success;
    private String message;
    private SubscriptionEntity subscription;
}
