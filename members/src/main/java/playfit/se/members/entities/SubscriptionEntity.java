package playfit.se.members.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import playfit.se.members.enums.SubscriptionType;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table
public class SubscriptionEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private SubscriptionType type; // "INDIVIDUAL", "FAMILY", STUDENT

    @ManyToOne
    private PricingEntity pricing;

    private Integer durationInMonths;



    // Linked activities can be optional based on the application logic
    // private List<ActivityGroupEntity> linkedActivities;

}
