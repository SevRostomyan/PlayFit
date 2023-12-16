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

    private String GroupName;

    @OneToMany(mappedBy = "sportGroup")
    private List<Student> groupMembers;

    @ManyToMany(mappedBy = "sportGroups")
    private Set<Trainer> trainers = new HashSet<>();

    @OneToMany(mappedBy = "sportGroup")
    private List<SportsSessions> sportsSessions;
}
