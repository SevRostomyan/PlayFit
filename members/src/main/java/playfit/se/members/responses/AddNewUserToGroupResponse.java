package playfit.se.members.responses;

import lombok.Data;

@Data
public class AddNewUserToGroupResponse {
    private boolean success;
    private String message;
}
