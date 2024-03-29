package playfit.se.members.entities;

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
public class AddressEntity {
     @Id
     @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "addressEntityGenerator")
     @SequenceGenerator(name = "addressEntityGenerator", sequenceName = "addressEntitySeq", allocationSize = 1)
    private Long id;
    private String street;
    private String zipcode;
    private String city;
    @OneToMany(mappedBy = "addressEntity")
    private List<UserEntity> userEntity;

}
