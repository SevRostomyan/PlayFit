package playfit.se.members.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Organisation_Club {
    @Id
    @GeneratedValue
    private Integer id;
    private String orgNr_PerNr;
    private String orgRepresentative;
    private String address;
    private String zipCode;
    private String city;
    private String mobile;
    private String eMail;





}
