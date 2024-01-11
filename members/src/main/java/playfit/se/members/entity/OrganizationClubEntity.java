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
public class OrganizationClubEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OrganizationClubGenerator")
    @SequenceGenerator(name = "OrganizationClubGenerator", sequenceName = "OrganizationClubSeq", allocationSize = 1)
    private Long id;
    private String orgNr;
    @OneToOne
    private UserEntity representative;
    private String address;
    private String zipCode;
    private String city;
    private String mobile;
    private String eMail;
    private String password;
}
