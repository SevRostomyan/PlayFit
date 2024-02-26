package playfit.se.members.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String nameOfSession;
    private LocalDate passDate;
    private boolean isPresent;
//    @ElementCollection
//    private List<Long> userId;
    @ManyToOne
    private ActivityGroupEntity activityGroupEntity;
}
