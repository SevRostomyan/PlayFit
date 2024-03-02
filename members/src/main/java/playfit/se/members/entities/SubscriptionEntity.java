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
