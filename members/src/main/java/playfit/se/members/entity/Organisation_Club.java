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
    private String Orgnr_Pernr;
    private String Kontat;
    private String KontaktAnsvarig;
    private String adress;
    private String mobil;
    private String ePost;





}
