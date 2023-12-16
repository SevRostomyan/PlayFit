package playfit.se.members.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SportsSessions {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "sport_group_id")
    private SportGroup sportGroup;

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;
}
