package playfit.se.members.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import playfit.se.members.entities.UserEntity;

import java.util.List;

@Entity
@Data
@Table
@NoArgsConstructor
@AllArgsConstructor
public class ClubEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "clubGenerator")
    @SequenceGenerator(name = "clubGenerator", sequenceName = "clubSeq", allocationSize = 1)
    private Long id;
    private String orgNr;
    private String orgName;
    @ManyToMany (mappedBy = "clubEntity")
    private List<UserEntity> userEntities;
    private String address;
    private String zipCode;
    private String city;
    private String mobile;
}
