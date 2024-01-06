package playfit.se.members.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {
    @Id
    @GeneratedValue
    private Integer id;
    private String invoiceFilePath; // Path or URL to the invoice file   (Ej använt än. Behöver logik för att hämta filer)

    @OneToOne(mappedBy = "invoice")
    private Notification notification;

    private String invoice_num;

    @ManyToOne
    private UserEntity userEntity;

    //@ManyToOne
    //private Subscription TrainingSubscription;  //The trainings that students pay for on monthly or yearly basis

    private Double totaltAmount;
    private Double priceExclVAT;
    private LocalDate invoiceDate;
    private String dueDate;


    // Organisational details
    private String ClubName;
    private String organisationalNumber;
    private String organisationAddress;

    // Customer details
    private String memberFirstName;
    private String memberLastName;
    private String memberEmail;
}
