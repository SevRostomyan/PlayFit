package playfit.se.members.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String invoiceFilePath; // Path or URL to the invoice file   (Ej använt än. Behöver logik för att hämta filer)

    @OneToOne
    private NotificationEntity notificationEntity;

    private String invoiceNum;

    @ManyToOne
    private UserEntity userEntity;

    @ManyToOne
    private ClubEntity clubEntity;

    @ManyToOne
    private SubscriptionEntity subscriptionEntity;

    private Double totaltAmount;
    private Double priceExclVAT;
    private LocalDate invoiceDate;
    private String dueDate;

    private String invoiceStatus; // "PAID", "UNPAID"
    private String paymentMethod; // "CREDIT_CARD", "BANK_TRANSFER"
    private Double discount; // Discount applied to the invoice

    // Organisational details
    private String ClubName;
    private String organizationalNumber;
    private String organizationAddress;

    // Customer details
    private String memberFirstName;
    private String memberLastName;
    private String memberEmail;
}
