package playfit.se.members.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AssignUsersDTO {

    private List<Long> userIds;
}
