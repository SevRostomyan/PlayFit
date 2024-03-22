package playfit.se.members.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "session_data")
public class SessionEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String nameOfSession;
    private LocalDateTime passDateTime;

    @OneToMany(mappedBy = "session")
    private List<Attendance> attendances;

    @ManyToMany
    @JoinTable(
            name = "user_session_data",
            joinColumns = @JoinColumn(name = "session_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<UserEntity> users;

    @ManyToOne
    private ActivityGroupEntity activityGroupEntity;
    @ManyToOne
    private PricingEntity pricing;


}
