package playfit.se.members.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Activity_Group_Entity {
    @Id
    @GeneratedValue
    private Long id;

    private String SportName; //ändra till aktivitetsNamn
    private int number_of_sessions;

    @OneToOne
    private Attendance_Entity attendanceEntity;
    @OneToOne
    private User_Entity userEntity;
}
