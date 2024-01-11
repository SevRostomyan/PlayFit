package playfit.se.members.DTOs;

import lombok.Data;
import playfit.se.members.entity.UserEntity;

@Data
public class OrganizationClubDTO {
    private String orgNr;
    private UserEntity representative;
    private String address;
    private String zipCode;
    private String city;
    private String mobile;
    private String eMail;
}
