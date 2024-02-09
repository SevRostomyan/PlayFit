package playfit.se.members.DTOs;

import lombok.Data;

@Data
public class SignUpClubDTO {
    private String orgNr;
    private String orgName;
    private SignUpUserEntityDTO representative;
    private AddressDTO representativeAddress;
    private String address;
    private String zipCode;
    private String city;
    private String mobile;
}
