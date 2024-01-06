package playfit.se.members.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityGroup {
    @Id
    @GeneratedValue
    private Integer id;

    private String SportName; //ändra till aktivitetsNamn
    private int number_of_sessions;

    @OneToOne
    private Attendance attendance;
    @OneToOne
    private UserEntity userEntity;
}
