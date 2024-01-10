package playfit.se.members.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Attendance_Entity {
    @Id
    @GeneratedValue
    private Long id;
    private LocalDate passDate;
    private boolean isPresent;

    @OneToOne
    private User_Entity userEntity;
}
