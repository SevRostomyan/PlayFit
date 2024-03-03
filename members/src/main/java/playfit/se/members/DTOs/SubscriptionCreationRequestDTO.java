package playfit.se.members.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import playfit.se.members.enums.SubscriptionType;

@Data
@AllArgsConstructor
public class SubscriptionCreationRequestDTO {
    private SubscriptionType type;
    private Long pricingId;
    private Integer durationInMonths;
}
