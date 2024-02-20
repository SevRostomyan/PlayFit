package playfit.se.members.responses;

import lombok.Data;

@Data
public class UserLogInResponse {
    private boolean success;
    private String message;
    private String token;
}
