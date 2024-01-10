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
public class User_Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userEntity_generator")
    @SequenceGenerator(name = "userEntity_generator", sequenceName = "userEntity_seq", allocationSize = 1)
    private Long id;
    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private String personal_number;
    private String gender;
    private String mobile;
    private boolean status;

    @OneToOne(cascade = CascadeType.ALL)
    private Address_Entity addressEntity;

    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = Role.class)
    private List<Role> role;

    @ManyToMany
    @JoinTable(
            name = "User_Entity_Guardian_Entity",
            joinColumns = @JoinColumn(name = "User_Entity_id"),
            inverseJoinColumns = @JoinColumn(name = "Guardian_Entity_id")
    )
    private List<Guardian_Entity> guardian_Entity_List;
}
