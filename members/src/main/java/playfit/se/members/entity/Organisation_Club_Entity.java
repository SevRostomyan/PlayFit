package playfit.se.members.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table
@NoArgsConstructor
@AllArgsConstructor
public class Organisation_Club_Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Organisation_Club_generator")
    @SequenceGenerator(name = "Organisation_Club_generator", sequenceName = "Organisation_Club_seq", allocationSize = 1)
    private Long id;
    private String orgNr_PerNr;
    private String orgRepresentative;
    private String address;
    private String zipCode;
    private String city;
    private String mobile;
    private String eMail;
    private String password;

}
