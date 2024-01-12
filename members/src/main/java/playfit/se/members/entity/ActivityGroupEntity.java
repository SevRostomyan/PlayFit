package playfit.se.members.entity;

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

    private String SportName; //ändra till aktivitetsNamn
    private int numberOfSessions;

    @OneToOne
    private AttendanceEntity attendanceEntity;
    @OneToMany (mappedBy = "activityGroupEntity")
    private List<UserEntity> userEntity;
}
