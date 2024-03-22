package playfit.se.members.responses;

import lombok.Data;
import playfit.se.members.entities.PricingEntity;

@Data
public class CreatePricingResponse {
    private boolean success;
    private String message;
    private PricingEntity pricingEntity;
}
