package playfit.se.members.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
 @Entity
public class Address {
     @Id
    private int id;
    private String street;
    private String zipcode;
    private String city;
}
