package playfit.se.members.DTOs;
import lombok.Data;

@Data
public class SignUpClubWithExistingUserDTO {
    private String orgNr;
    private String clubName;
    private String address;
    private String zipCode;
    private String city;
    private String mobile;
}
