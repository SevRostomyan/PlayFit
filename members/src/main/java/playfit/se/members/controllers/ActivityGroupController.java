package playfit.se.members.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import playfit.se.members.DTOs.ActivityGroupDTO;
import playfit.se.members.DTOs.SessionDTO;
import playfit.se.members.responses.AddNewUserToGroupResponse;
import playfit.se.members.responses.CreateActivityResponse;
import playfit.se.members.responses.CreateSessionResponse;
import playfit.se.members.services.ActivityGroupService;

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

    @PostMapping("create-session/{activityId}")
    public ResponseEntity<String> createSession(@RequestBody SessionDTO sessionDTO, @PathVariable Long activityId){
        CreateSessionResponse response = activityGroupService.createSession(sessionDTO, activityId);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response.getMessage());
        } else {
            return ResponseEntity.badRequest().body(response.getMessage());
        }
    }

    @PostMapping("/addMemberToGroup/{activityGroupId}")
    public ResponseEntity<String> addUsersToActivityGroup(@PathVariable Long activityGroupId, @RequestBody ActivityGroupDTO activityGroupDTO) {
        AddNewUserToGroupResponse response = activityGroupService.addUsersToActivityGroup(activityGroupId, activityGroupDTO.getUserIds());

        if (response.isSuccess()) {
            return ResponseEntity.ok(response.getMessage());
        } else {
            return ResponseEntity.badRequest().body(response.getMessage());
        }
    }

}
