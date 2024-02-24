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
    private Double price;
    private Integer durationInMonths;

    @ManyToMany
    @JoinTable(
            name = "subscription_activity_groups",
            joinColumns = @JoinColumn(name = "subscription_id"),
            inverseJoinColumns = @JoinColumn(name = "activity_group_id")
    )
    private List<ActivityGroupEntity> linkedActivities; // Linked activities to this subscription
}
