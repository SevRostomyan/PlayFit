package playfit.se.members.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PricingDTO {
    private Double price; // Standard price
    private Double discount; // Optional discount rate
}
