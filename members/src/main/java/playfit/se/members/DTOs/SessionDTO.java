package playfit.se.members.DTOs;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import playfit.se.members.entities.ActivityGroupEntity;

import java.time.LocalDate;
import java.util.List;


@Data
@AllArgsConstructor
public class SessionDTO {

    private String nameOfSession;
    private LocalDate passDate;
    private List<Long> userIds;
    private Long activityGroupId;
    private Long pricingId;
}
