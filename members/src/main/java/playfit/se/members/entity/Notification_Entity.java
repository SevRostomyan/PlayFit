package playfit.se.members.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@Table
@NoArgsConstructor
@AllArgsConstructor
public class Notification_Entity {
    @Id
    @GeneratedValue
    private Long id ;
    private String subject;
    private String content;
    private LocalDate timestamp;
    @OneToOne
    @JoinColumn(name = "id")
    private Invoice_Entity invoiceEntity;
    @ManyToOne
    private User_Entity userEntity;
}
