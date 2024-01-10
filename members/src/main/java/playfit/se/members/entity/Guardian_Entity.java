
package playfit.se.members.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import playfit.se.members.entity.User_Entity;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table
public class Guardian_Entity{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "guardianEntity_generator")
    @SequenceGenerator(name = "guardianEntity_generator", sequenceName = "guardianEntity_seq", allocationSize = 1)
    private Long guardian_ID;

    @ManyToMany(mappedBy = "guardian_Entity_List")
    private List<User_Entity> list_of_userEntity;

}
