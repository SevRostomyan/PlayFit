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
    @GeneratedValue
    private Integer id;
    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private String personal_number;
    private String gender;
    private String mobile;
    private boolean status;

    @OneToOne
    private Address address;

    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = Role.class)
    private List<Role> role;

    @ManyToMany
    private List<Organisation_Club> organisationClub;
}
