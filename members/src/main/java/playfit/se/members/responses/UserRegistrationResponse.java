package playfit.se.members.responses;

import lombok.Data;

@Data
public class UserRegistrationResponse {
    private boolean success;
    private String message;
}
