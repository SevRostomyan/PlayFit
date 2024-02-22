package playfit.se.members.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import playfit.se.members.DTOs.ActivityGroupDTO;
import playfit.se.members.DTOs.SessionDTO;
import playfit.se.members.entities.ActivityGroupEntity;
import playfit.se.members.entities.SessionEntity;
import playfit.se.members.entities.UserEntity;
import playfit.se.members.repositories.ActivityGroupRepository;
import playfit.se.members.repositories.SessionRepository;
import playfit.se.members.repositories.UserEntityRepository;
import playfit.se.members.responses.AddNewUserToGroupResponse;
import playfit.se.members.responses.AddTrainerToActivityGroupResponse;
import playfit.se.members.responses.CreateActivityResponse;
import playfit.se.members.responses.CreateSessionResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ActivityGroupService {

    private final ActivityGroupRepository activityGroupRepository;
    private final SessionRepository sessionRepository;
    private final UserEntityRepository userEntityRepository;

    public CreateActivityResponse createActivity(ActivityGroupDTO activityGroupDTO) {
        Optional<ActivityGroupEntity> activityGroupEntityOptional = activityGroupRepository.findByActivityName(activityGroupDTO.getActivityName());
        CreateActivityResponse response = new CreateActivityResponse();

        if (activityGroupEntityOptional.isPresent()) {
            response.setSuccess(false);
            response.setMessage("Name of activity is in use, please choose another name!");
        } else {
            ActivityGroupEntity newActivityGroupEntity = new ActivityGroupEntity();
            newActivityGroupEntity.setActivityName(activityGroupDTO.getActivityName());
            activityGroupRepository.save(newActivityGroupEntity);
            response.setSuccess(true);
            response.setMessage("Created a new activity group!" + activityGroupDTO.getActivityName());
        }
        return response;
    }

    public CreateSessionResponse createSession(SessionDTO sessionDTO, Long activityId) {

        CreateSessionResponse response = new CreateSessionResponse();
        SessionEntity sessionEntity = new SessionEntity();
        Optional<ActivityGroupEntity> activityGroupOptional = activityGroupRepository.findById(activityId);




        if (activityGroupOptional.isPresent()) {
            ActivityGroupEntity activityGroupEntity = activityGroupOptional.get();
            List<SessionEntity> sessions = activityGroupEntity.getSessionEntities();
            sessionEntity.setNameOfSession(sessionDTO.getNameOfSession());
            sessionEntity.setActivityGroupEntity(activityGroupEntity);
            sessionRepository.save(sessionEntity);

            sessions.add(sessionEntity);

            activityGroupEntity.setSessionEntities(sessions);
            activityGroupRepository.save(activityGroupEntity);
            response.setSuccess(true);
            response.setMessage("Created a new activity group!");
        } else {
            throw new IllegalArgumentException("Activity not found");
        }

        return response;
    }

    public AddNewUserToGroupResponse addUsersToActivityGroup(Long activityGroupId, List<Long> userIds) {
        ActivityGroupEntity activityGroup = activityGroupRepository.findById(activityGroupId)
                .orElseThrow(() -> new IllegalArgumentException("Activity group not found"));

        AddNewUserToGroupResponse response = new AddNewUserToGroupResponse();
        List<UserEntity> users = userEntityRepository.findAllById(userIds);
        for (UserEntity user : users) {
            user.getActivityGroups().add(activityGroup);
        }
        userEntityRepository.saveAll(users);

        response.setSuccess(true);
        response.setMessage("Added member to group");

        return response;
    }

    public AddTrainerToActivityGroupResponse addTrainerToActivityGroup(Long activityGroupId, Long userId) {
        AddTrainerToActivityGroupResponse response = new AddTrainerToActivityGroupResponse();
        ActivityGroupEntity activityGroup = activityGroupRepository.findById(activityGroupId)
                .orElseThrow(() -> new IllegalArgumentException("Activity group not found"));
        UserEntity userEntity = userEntityRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));


        activityGroup.getTrainers().add(userEntity);
        userEntity.getTrainerForGroups().add(activityGroup);

        activityGroupRepository.save(activityGroup);
        userEntityRepository.save(userEntity);

        response.setSuccess(true);
        response.setMessage("Added member to group");
        return response;
    }

}
