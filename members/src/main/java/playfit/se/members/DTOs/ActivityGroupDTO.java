package playfit.se.members.DTOs;

import lombok.Data;

import java.util.List;

@Data
public class ActivityGroupDTO {

    private String activityName;
    private List<Long> userIds;

}
