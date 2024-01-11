package playfit.se.members.DTOs;

import lombok.Data;

@Data
public class AddressDTO {
    private String street;
    private String zipcode;
    private String city;
}
