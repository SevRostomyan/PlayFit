package playfit.se.members.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import playfit.se.members.token.Token;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


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
    private List<RoleEntity> roles;

    @ManyToMany
    @JoinTable(
            name = "UserEntityGuardianEntity",
            joinColumns = @JoinColumn(name = "UserEntityId"),
            inverseJoinColumns = @JoinColumn(name = "GuardianEntityId")
    )
    private List<GuardianEntity> guardianEntityList;
    //    private Long orgId;
    @ManyToOne
    private ActivityGroupEntity activityGroupEntity;
    @OneToMany(mappedBy = "userEntity")
    private List<Token> tokens;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        for (RoleEntity role : this.roles) {
            authorities.add(new SimpleGrantedAuthority(role.getType()));
        }
        return authorities;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
