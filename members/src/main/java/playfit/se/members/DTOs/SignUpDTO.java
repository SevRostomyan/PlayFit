package playfit.se.members.DTOs;

import lombok.Data;
import playfit.se.members.entities.AddressEntity;
@Data
public class SignUpDTO {
    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private String personalNumber;
    private String gender;
    private String mobile;
    private AddressEntity addressEntity;
    private Long orgId;
}
