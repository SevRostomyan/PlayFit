package playfit.se.members.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import playfit.se.members.enums.Role;
import playfit.se.members.token.Token;


import java.util.*;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table
public class UserEntity implements UserDetails {

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
    private boolean accountStatus = false;
    private boolean loginStatus = false;
    @ManyToOne
    private ClubEntity clubEntity;
    @ManyToOne(cascade = CascadeType.ALL)
    private AddressEntity addressEntity;

    @ElementCollection(targetClass = Role.class)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @ManyToMany
    @JoinTable(
            name = "UserEntityGuardianEntity",
            joinColumns = @JoinColumn(name = "UserEntityId"),
            inverseJoinColumns = @JoinColumn(name = "GuardianEntityId")
    )
    private List<GuardianEntity> guardianEntityList;
    //    private Long orgId;
    @ManyToMany
    @JoinTable(
            name = "user_activity_group",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "activity_group_id")
    )
    private List<ActivityGroupEntity> activityGroups;
    @ManyToMany(mappedBy = "trainers")
    private List<ActivityGroupEntity> trainerForGroups;
    @OneToMany(mappedBy = "userEntity")
    private List<Token> tokens;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .toList();
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
