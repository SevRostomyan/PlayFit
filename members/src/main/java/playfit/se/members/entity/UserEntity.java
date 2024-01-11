package playfit.se.members.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import playfit.se.members.enums.Role;


import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userEntityGenerator")
    @SequenceGenerator(name = "userEntityGenerator", sequenceName = "userEntitySeq", allocationSize = 1)
    private Long id;
    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private String personalNumber;
    private String gender;
    private String mobile;
    private boolean status;
    @OneToOne(mappedBy = "representative")
    @JoinTable(
            name = "organization",
            joinColumns = @JoinColumn(name= "id")
    )
    private OrganizationClubEntity organizationClubEntity;
    @OneToOne(cascade = CascadeType.ALL)
    private AddressEntity addressEntity;

    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = Role.class)
    private List<Role> role;

    @ManyToMany
    @JoinTable(
            name = "UserEntityGuardianEntity",
            joinColumns = @JoinColumn(name = "UserEntityId"),
            inverseJoinColumns = @JoinColumn(name = "GuardianEntityId")
    )
    private List<GuardianEntity> guardianEntityList;
}
