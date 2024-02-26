package playfit.se.members.DTOs;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.OneToOne;
import lombok.Data;
import playfit.se.members.entities.ActivityGroupEntity;

import java.time.LocalDate;
import java.util.List;

@Data
public class SessionDTO {

    private Long id;
    private String nameOfSession;
    private LocalDate passDate;
    private boolean isPresent;
    private List<Long> userId;
    private ActivityGroupEntity activityGroupEntity;
}
