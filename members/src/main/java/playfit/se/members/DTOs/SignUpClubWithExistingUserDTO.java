package playfit.se.members.DTOs;
import lombok.Data;

@Data
public class SignUpClubWithExistingUserDTO {
    private String orgNr;
    private String orgName;
    private String address;
    private String zipCode;
    private String city;
    private String mobile;
}
