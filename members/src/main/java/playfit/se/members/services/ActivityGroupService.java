package playfit.se.members.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;
import playfit.se.members.DTOs.ActivityGroupDTO;
import playfit.se.members.DTOs.SessionDTO;
import playfit.se.members.entities.*;
import playfit.se.members.DTOs.UserForActivityGroupDTO;
import playfit.se.members.entities.ActivityGroupEntity;
import playfit.se.members.entities.SessionEntity;
import playfit.se.members.entities.UserEntity;
import playfit.se.members.repositories.ActivityGroupRepository;
import playfit.se.members.repositories.PricingRepository;
import playfit.se.members.repositories.SessionRepository;
import playfit.se.members.repositories.UserEntityRepository;
import playfit.se.members.responses.AddNewUserToGroupResponse;
import playfit.se.members.responses.AddTrainerToActivityGroupResponse;
import playfit.se.members.responses.CreateActivityResponse;
import playfit.se.members.responses.CreateSessionResponse;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActivityGroupService {

    private final ActivityGroupRepository activityGroupRepository;
    private final SessionRepository sessionRepository;
    private final PricingRepository pricingRepository;
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
            response.setMessage("Created a new activity group! " + activityGroupDTO.getActivityName());
        }
        return response;
    }

    public CreateSessionResponse createSession(SessionDTO sessionDTO, Long activityId) {

        CreateSessionResponse response = new CreateSessionResponse();
        try {
            SessionEntity sessionEntity = new SessionEntity();
            sessionEntity.setNameOfSession(sessionDTO.getNameOfSession());
            sessionEntity.setPassDate(sessionDTO.getPassDate());

            ActivityGroupEntity activityGroup = activityGroupRepository.findById(activityId)
                    .orElseThrow(() -> new IllegalArgumentException("Activity not found"));
            sessionEntity.setActivityGroupEntity(activityGroup);

            PricingEntity pricing = pricingRepository.findById(sessionDTO.getPricingId())
                    .orElseThrow(() -> new IllegalArgumentException("Pricing not found"));
            sessionEntity.setPricing(pricing);

            // Handling user attendance
            List<UserEntity> users = userEntityRepository.findAllById(sessionDTO.getUserIds());
            for (UserEntity user : users) {
                Attendance attendance = new Attendance();
                attendance.setUser(user);
                attendance.setSession(sessionEntity);
                sessionEntity.getAttendances().add(attendance);
            }

            sessionRepository.save(sessionEntity);
            response.setSuccess(true);
            response.setMessage("Session created successfully!");
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage("Failed to create session: " + e.getMessage());
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

    public List<ActivityGroupDTO> getActivityGroups() {
        List<ActivityGroupEntity> activityGroups = activityGroupRepository.findAll();
        return activityGroups.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    private ActivityGroupDTO convertToDTO(ActivityGroupEntity activityGroup) {
        ActivityGroupDTO dto = new ActivityGroupDTO();
        dto.setId(activityGroup.getId());
        dto.setActivityName(activityGroup.getActivityName());
        dto.setUsers(activityGroup.getUsers().stream()
                .map(this::convertUserToDTO)
                .collect(Collectors.toList()));
        dto.setSessions(activityGroup.getSessionEntities().stream()
                .map(this::convertSessionToDTO)
                .collect(Collectors.toList()));
        return dto;
    }
    private UserForActivityGroupDTO convertUserToDTO(UserEntity user) {
        UserForActivityGroupDTO dto = new UserForActivityGroupDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        return dto;
    }
    private SessionDTO convertSessionToDTO(SessionEntity session) {
        SessionDTO dto = new SessionDTO();
        dto.setId(session.getId());
        dto.setNameOfSession(session.getNameOfSession());
        dto.setPassDate(session.getPassDate());
        dto.setPresent(session.isPresent());
        return dto;
    }
    public List<UserForActivityGroupDTO> getUsersInActivityGroup(Long activityGroupId) {
        ActivityGroupEntity activityGroup = activityGroupRepository.findById(activityGroupId)
                .orElseThrow(() -> new IllegalArgumentException("Activity group not found"));

        return activityGroup.getUsers().stream()
                .map(this::convertUserToDTO)
                .collect(Collectors.toList());
    }

}
