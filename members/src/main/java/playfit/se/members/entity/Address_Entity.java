package playfit.se.members.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table
public class Address_Entity {
     @Id
     @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "addressEntity_generator")
     @SequenceGenerator(name = "addressEntity_generator", sequenceName = "addressEntity_seq", allocationSize = 1)
    private Long id;
    private String street;
    private String zipcode;
    private String city;

}
