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
@Table
public class ActivityGroupEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String activityName;

    @ManyToOne
    private PricingEntity pricing;

    @ManyToMany(mappedBy = "activityGroups")
    private List<UserEntity> users;

    @OneToMany(mappedBy = "activityGroupEntity")
    private List<SessionEntity> sessionEntities;

    @ManyToMany
    @JoinTable(
            name = "activity_group_trainers",
            joinColumns = @JoinColumn(name = "activity_group_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<UserEntity> trainers;
}
