package playfit.se.members.DTOs;

import lombok.Data;

@Data
public class PricingDTO {
    private Double price; // Standard price
    private Double discount; // Optional discount rate
}
