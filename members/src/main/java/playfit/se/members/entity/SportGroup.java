package playfit.se.members.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SportGroup {
    @Id
    @GeneratedValue
    private Integer id;

    private String SportName;
    private int number_of_sessions;

    @OneToOne
    private Attendance attendance;
    @OneToOne
    private UserEntity userEntity;
}
