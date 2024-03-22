package playfit.se.members.DTOs;

import lombok.Data;

// DTO för enkel presentation av aktivitetsgrupper
@Data
public class SimpleActivityGroupDTO {
    private Long id;
    private String activityName;
    // andra fält om nödvändigt
}
