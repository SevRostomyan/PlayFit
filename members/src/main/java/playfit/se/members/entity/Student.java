package playfit.se.members.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import playfit.se.members.enums.StudentLevel;

@Entity
//@Data
@NoArgsConstructor
//@AllArgsConstructor
public class Student extends Member{


    private StudentLevel level;

    @ManyToOne
    @JoinColumn(name = "sport_group_id")
    private SportGroup sportGroup;
}
