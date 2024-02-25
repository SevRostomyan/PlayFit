package playfit.se.members.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String type; // "INDIVIDUAL", "FAMILY"

    @ManyToOne
    private PricingEntity pricing;

    private Integer durationInMonths;

    @ManyToMany
    @JoinTable(
            name = "user_subscriptions",
            joinColumns = @JoinColumn(name = "subscription_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<UserEntity> users; // Users subscribed to this subscription

    // Linked activities can be optional based on your application logic
    // private List<ActivityGroupEntity> linkedActivities;

}
