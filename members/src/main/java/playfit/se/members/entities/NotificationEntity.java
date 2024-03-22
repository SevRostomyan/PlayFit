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
@Table
public class NotificationEntity {
    @Id
    @GeneratedValue
    private Long id ;
    private String subject;
    private String content;
    private LocalDate timestamp;
    @OneToOne
    private InvoiceEntity invoiceEntity;
    @ManyToOne
    private UserEntity userEntity;
}
