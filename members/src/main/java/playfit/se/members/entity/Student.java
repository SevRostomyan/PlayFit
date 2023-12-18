package playfit.se.members.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.NoArgsConstructor;
import playfit.se.members.enums.StudentLevel;

@Entity
//@Data
@NoArgsConstructor
//@AllArgsConstructor
public class Student extends UserEntity {


    private StudentLevel level;

    @ManyToOne
    @JoinColumn(name = "sport_group_id")
    private SportGroup sportGroup;
}
