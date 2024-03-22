package playfit.se.members.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table
public class PricingEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String name; // Name of the pricing model

    private Double price; // Standard price

    private Double discount; // Optional discount rate

    // Additional fields like startDate, endDate for special pricing

    // You can add more fields to handle complex pricing models
}