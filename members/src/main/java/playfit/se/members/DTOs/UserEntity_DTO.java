package playfit.se.members.DTOs;

import lombok.Data;
import playfit.se.members.entity.Address_Entity;
@Data
public class UserEntity_DTO {
    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private String personal_number;
    private String gender;
    private String mobile;
    private Address_Entity addressEntity;
}
