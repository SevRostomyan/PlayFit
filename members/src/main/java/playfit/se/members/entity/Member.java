package playfit.se.members.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import playfit.se.members.enums.UserRole;



@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue
    private Integer id;
    private String address;
    private String firstname;
    private String lastname;
    private String gender;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    //private List<Notifications> notifications;
}
