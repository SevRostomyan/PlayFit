
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
@Table
public class GuardianEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "guardianEntityGenerator")
    @SequenceGenerator(name = "guardianEntityGenerator", sequenceName = "guardianEntitySeq", allocationSize = 1)
    private Long guardianID;

    @ManyToMany(mappedBy = "guardianEntityList")
    private List<UserEntity> listOfUserEntity;

}
