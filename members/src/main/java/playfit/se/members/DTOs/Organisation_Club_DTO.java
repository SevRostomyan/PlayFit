package playfit.se.members.DTOs;

import lombok.Data;

@Data
public class Organisation_Club_DTO {
    private String orgNr_PerNr;
    private String orgRepresentative;
    private String address;
    private String zipCode;
    private String city;
    private String mobile;
    private String eMail;
}
