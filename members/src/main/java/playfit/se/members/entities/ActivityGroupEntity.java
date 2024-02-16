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
public class ActivityGroupEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String activityName;
    @ManyToMany(mappedBy = "activityGroups")
    private List<UserEntity> users;
    @OneToMany(mappedBy = "activityGroupEntity")
    private List<SessionEntity> sessionEntities;
}
