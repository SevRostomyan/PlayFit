package playfit.se.members.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Entity
@Data
@Table
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationClubEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OrganizationClubGenerator")
    @SequenceGenerator(name = "OrganizationClubGenerator", sequenceName = "OrganizationClubSeq", allocationSize = 1)
    private Long id;
    private String orgNr;
    private String orgName;
    @OneToMany(mappedBy = "organizationClubEntity")
    private List<UserEntity> userEntities;
    private String address;
    private String zipCode;
    private String city;
    private String mobile;
    private String email;
    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        for (UserEntity userEntity : userEntities) {
            authorities.addAll(
                    userEntity.getRole().stream()
                            .map(role -> new SimpleGrantedAuthority(role.name()))
                            .toList()
            );
        }

        return authorities;
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
