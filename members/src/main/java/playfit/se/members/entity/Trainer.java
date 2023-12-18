package playfit.se.members.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import playfit.se.members.enums.TrainerSpeciality;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
//@Data
@NoArgsConstructor
//@AllArgsConstructor
public class Trainer extends Member{

    private TrainerSpeciality speciality;

    @OneToMany(mappedBy = "trainer")
    private List<SportsSessions> sportsSessions;

    @ManyToMany
    @JoinTable(
            name = "trainer_sportgroup",
            joinColumns = @JoinColumn(name = "trainer_id"),
            inverseJoinColumns = @JoinColumn(name = "sportgroup_id")
    )
    private Set<SportGroup> sportGroups = new HashSet<>();

}
