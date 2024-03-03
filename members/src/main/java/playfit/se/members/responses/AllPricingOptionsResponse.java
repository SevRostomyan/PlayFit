package playfit.se.members.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import playfit.se.members.entities.PricingEntity;

import java.util.List;

@Data
@AllArgsConstructor
public class AllPricingOptionsResponse {
    private boolean success;
    private String message;
    private List<PricingEntity> pricingOptions;
}
