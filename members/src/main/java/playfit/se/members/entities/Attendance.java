package playfit.se.members.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table
public class Attendance {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private SessionEntity session;
    @ManyToOne
    private UserEntity user;

    private boolean isInvited;
    private boolean isPresent;
}
