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
    private String firstName;
    private String lastName;
    private String personalNumber;
    private String gender;
    private String mobile;
    private boolean accountStatus = false; // if it is active or deleted
    private boolean loginStatus = false;  // if is online or not.
    @ManyToMany
    @JoinTable(
            name = "UserEntityClubEntity",
            joinColumns = @JoinColumn(name = "UserEntityId"),
            inverseJoinColumns = @JoinColumn(name = "ClubEntityId")
    )
    private List<ClubEntity> clubEntity;
    @ManyToOne(cascade = CascadeType.ALL)
    private AddressEntity addressEntity;

    @OneToMany(mappedBy = "user" )
    private List<RoleEntity> role;
    @ManyToMany
    @JoinTable(
            name = "UserEntityGuardianEntity",
            joinColumns = @JoinColumn(name = "UserEntityId"),
            inverseJoinColumns = @JoinColumn(name = "GuardianEntityId")
    )
    private List<GuardianEntity> guardianEntityList;
    //private Long orgId;
    @ManyToOne
    private ActivityGroupEntity activityGroupEntity;
}
