package playfit.se.members.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionDTO {

    private Long id;
    private String nameOfSession; // Name of the session, required for session creation
    private LocalDateTime passDateTime;
    private List<Long> userIds;
    private Long activityGroupId;
    private Long pricingId;
}
