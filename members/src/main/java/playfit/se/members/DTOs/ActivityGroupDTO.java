package playfit.se.members.DTOs;

import lombok.Data;

import java.util.List;

@Data
public class ActivityGroupDTO {

    private Long id;
    private String activityName;
    private List<UserForActivityGroupDTO> users;
    private List<SessionDTO> sessions;
    private List<Long> userIds;
}
