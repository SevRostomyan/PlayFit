package playfit.se.members.DTOs;

import lombok.Data;

@Data
public class SignUpUserEntityDTO {
    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private String personalNumber;
    private String gender;
    private String mobile;
    private AddressDTO addressDTO;
}
