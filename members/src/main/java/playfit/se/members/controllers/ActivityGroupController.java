package playfit.se.members.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import playfit.se.members.DTOs.*;
import playfit.se.members.responses.*;
import playfit.se.members.services.ActivityGroupService;

import java.util.List;

@RequestMapping("/api/v1/activities-group")
@RestController
@RequiredArgsConstructor
public class ActivityGroupController {
    private final ActivityGroupService activityGroupService;
    @PostMapping("/create-activity")
    public ResponseEntity<String> createActivity(@RequestBody ActivityGroupDTO activityGroupDTO){
        CreateActivityResponse response = activityGroupService.createActivity(activityGroupDTO);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response.getMessage());
        } else {
            return ResponseEntity.badRequest().body(response.getMessage());
        }
    }

    @GetMapping("/detailed-activity-groups")
    public List<ActivityGroupDTO> getDetailedActivityGroups() {
        return activityGroupService.getDetailedActivityGroups();
    }

    @GetMapping("/simple-activity-groups")
    public List<SimpleActivityGroupDTO> getSimpleActivityGroups() {
        return activityGroupService.getSimpleActivityGroups();
    }

    @GetMapping("/detailed-activity-groups/{activityGroupId}")
    public ActivityGroupDTO getDetailedActivityGroup(@PathVariable Long activityGroupId) {
        return activityGroupService.getDetailedActivityGroup(activityGroupId);
    }
    @GetMapping("/activity-groups/{activityGroupId}/users")
    public ResponseEntity<List<UserForActivityGroupDTO>> getUsersInActivityGroup(@PathVariable Long activityGroupId) {
        List<UserForActivityGroupDTO> users = activityGroupService.getUsersInActivityGroup(activityGroupId);
        return ResponseEntity.ok(users);
    }

    @PostMapping("/addUsersToActivityGroup/{activityGroupId}")
    public ResponseEntity<String> addUsersToActivityGroup(@PathVariable Long activityGroupId, @RequestBody ActivityGroupDTO activityGroupDTO) {
        AddNewUserToGroupResponse response = activityGroupService.addUsersToActivityGroup(activityGroupId, activityGroupDTO.getUserIds());
        if (response.isSuccess()) {
            return ResponseEntity.ok(response.getMessage());
        } else {
            return ResponseEntity.badRequest().body(response.getMessage());
        }
    }
    @PostMapping("/addTrainerToActivityGroup/{activityGroupId}/{userId}")
    public ResponseEntity<String> addTrainerToActivityGroup(@PathVariable Long activityGroupId, @PathVariable Long userId) {
        AddTrainerToActivityGroupResponse response = activityGroupService.addTrainerToActivityGroup(activityGroupId, userId);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response.getMessage());
        } else {
            return ResponseEntity.badRequest().body(response.getMessage());
        }
    }

    @PostMapping("/create-session/{activityId}")
    public ResponseEntity<CreateSessionResponse> createSession(@RequestBody SessionDTO sessionDTO, @PathVariable Long activityId) {
        CreateSessionResponse response = activityGroupService.createSession(sessionDTO, activityId);
        return response.isSuccess() ? ResponseEntity.ok(response) : ResponseEntity.badRequest().body(response);
    }

    @GetMapping("/activity-groups/{activityGroupId}/sessions")
    public ResponseEntity<List<SessionDTO>> getSessionsInActivityGroup(@PathVariable Long activityGroupId) {
        List<SessionDTO> sessionDTOs = activityGroupService.getSessionsInActivityGroup(activityGroupId);
        return ResponseEntity.ok(sessionDTOs);
    }

        @PostMapping("/assign-users-to-a-session/{sessionId}")
    public ResponseEntity<AssignUsersToSessionResponse> assignUsersToASession(@RequestBody AssignUsersDTO assignUsersDTO, @PathVariable Long sessionId) {
        AssignUsersToSessionResponse response = activityGroupService.assignUsersToASession(assignUsersDTO, sessionId);
        return response.isSuccess() ? ResponseEntity.ok(response) : ResponseEntity.badRequest().body(response);
    }

    @PostMapping("/assign-users-to-all-sessions/{activityGroupId}")
    public ResponseEntity<AssignUsersToSessionResponse> assignUsersToAllSessionsInAnActivityGroup(@RequestBody AssignUsersDTO assignUsersDTO, @PathVariable Long activityGroupId) {
        AssignUsersToSessionResponse response = activityGroupService.assignUsersToAllSessionsInAnActivityGroup(assignUsersDTO, activityGroupId);
        return response.isSuccess() ? ResponseEntity.ok(response) : ResponseEntity.badRequest().body(response);
    }

    @PostMapping("/check-in-user/{sessionId}/{userId}")
    public ResponseEntity<CheckInUserResponse> checkInUser(@PathVariable Long sessionId, @PathVariable Long userId) {
        CheckInUserResponse response = activityGroupService.checkInUser(sessionId, userId);
        return response.isSuccess() ? ResponseEntity.ok(response) : ResponseEntity.badRequest().body(response);
    }
}
