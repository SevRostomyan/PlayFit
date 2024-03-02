package playfit.se.members.DTOs;

import lombok.Data;
import playfit.se.members.enums.SubscriptionType;

@Data
public class SubscriptionCreationRequestDTO {
    private SubscriptionType type;
    private Long pricingId;
    private Integer durationInMonths;
}
