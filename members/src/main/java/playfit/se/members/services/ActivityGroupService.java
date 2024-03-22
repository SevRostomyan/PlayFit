package playfit.se.members.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import playfit.se.members.DTOs.*;
import playfit.se.members.entities.*;
import playfit.se.members.entities.ActivityGroupEntity;
import playfit.se.members.entities.SessionEntity;
import playfit.se.members.entities.UserEntity;
import playfit.se.members.repositories.*;
import playfit.se.members.responses.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActivityGroupService {

    private final ActivityGroupRepository activityGroupRepository;
    private final SessionRepository sessionRepository;
    private final AttendanceRepository attendanceRepository;
    private final PricingRepository pricingRepository;
    private final UserEntityRepository userEntityRepository;

    //____________________________________________________________
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


    //____________________________________________________________
    // Hämtar detaljerad information om alla aktivitetsgrupper inklusive samtliga användare och sessioner
    // (Kan belasta systemet om det finns många användare och sessioner)
    public List<ActivityGroupDTO> getDetailedActivityGroups() {
        List<ActivityGroupEntity> activityGroups = activityGroupRepository.findAll();
        return activityGroups.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Används i getDetailedActivityGroups ovan
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

    // Används i convertToDTO ovan
    private UserForActivityGroupDTO convertUserToDTO(UserEntity user) {
        UserForActivityGroupDTO dto = new UserForActivityGroupDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        return dto;
    }


    //____________________________________________________________
    // Hämtar enkel information om alla aktivitetsgrupper
    public List<SimpleActivityGroupDTO> getSimpleActivityGroups() {
        List<ActivityGroupEntity> activityGroups = activityGroupRepository.findAll();
        return activityGroups.stream()
                .map(this::convertToSimpleDTO)
                .collect(Collectors.toList());
    }

    // Används i getSimpleActivityGroups ovan
    private SimpleActivityGroupDTO convertToSimpleDTO(ActivityGroupEntity activityGroup) {
        SimpleActivityGroupDTO dto = new SimpleActivityGroupDTO();
        dto.setId(activityGroup.getId());
        dto.setActivityName(activityGroup.getActivityName());
        // sätt andra fält om nödvändigt
        return dto;
    }


    //____________________________________________________________
    // Metod för att hämta en specifik aktivitetsgrupp med detaljer inklusive användare och sessioner
    public ActivityGroupDTO getDetailedActivityGroup(Long activityGroupId) {
        ActivityGroupEntity activityGroup = activityGroupRepository.findById(activityGroupId)
                .orElseThrow(() -> new IllegalArgumentException("Activity group not found"));
        return convertToDTO(activityGroup);
    }


    //____________________________________________________________
    // Metod för att hämta alla användare i en aktivitetsgrupp
    public List<UserForActivityGroupDTO> getUsersInActivityGroup(Long activityGroupId) {
        ActivityGroupEntity activityGroup = activityGroupRepository.findById(activityGroupId)
                .orElseThrow(() -> new IllegalArgumentException("Activity group not found"));

        return activityGroup.getUsers().stream()
                .map(this::convertUserToDTO)
                .collect(Collectors.toList());
    }


    //____________________________________________________________
    public CreateSessionResponse createSession(SessionDTO sessionDTO, Long activityId) {
        CreateSessionResponse response = new CreateSessionResponse();
        try {
            SessionEntity sessionEntity = new SessionEntity();
            sessionEntity.setNameOfSession(sessionDTO.getNameOfSession());
            sessionEntity.setPassDateTime(sessionDTO.getPassDateTime());

            ActivityGroupEntity activityGroup = activityGroupRepository.findById(activityId)
                    .orElseThrow(() -> new IllegalArgumentException("Activity not found"));
            sessionEntity.setActivityGroupEntity(activityGroup);

            PricingEntity pricing;
            if (sessionDTO.getPricingId() != null) {
                pricing = pricingRepository.findById(sessionDTO.getPricingId())
                        .orElseThrow(() -> new IllegalArgumentException("Pricing not found"));
            } else {
                pricing = getDefaultPricing();
            }
            sessionEntity.setPricing(pricing);

            sessionRepository.save(sessionEntity);
            response.setSuccess(true);
            response.setMessage("Session created successfully!");
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage("Failed to create session: " + e.getMessage());
        }
        return response;
    }

    // Används i createSession ovan
    private PricingEntity getDefaultPricing() {
        // Fetch the default "Free Session" or standard price from the database
        // If it does not exist, create it
        return pricingRepository.findByName("Free Session")
                .orElseGet(() -> {
                    PricingEntity defaultPricing = new PricingEntity();
                    defaultPricing.setName("Free Session");
                    defaultPricing.setPrice(0.0); // Set to 0 or any standard price you prefer
                    return pricingRepository.save(defaultPricing);
                });
    }

    //Används för att hämta alla sessioner för en aktivitetsgrupp
    public List<SessionDTO> getSessionsInActivityGroup(Long activityGroupId) {
        List<SessionEntity> sessions = sessionRepository.findByActivityGroupEntityId(activityGroupId);
        return sessions.stream()
                .map(this::convertSessionToDTO)
                .collect(Collectors.toList());
    }

    // Används i getSessionsByActivityGroup ovan
    private SessionDTO convertSessionToDTO(SessionEntity session) {
        SessionDTO dto = new SessionDTO();
        dto.setId(session.getId());
        dto.setNameOfSession(session.getNameOfSession());
        dto.setPassDateTime(session.getPassDateTime());

        // Convert attendance list to a list of user IDs
        if (session.getAttendances() != null) {
            List<Long> userIds = session.getAttendances().stream()
                    .map(Attendance::getUser)
                    .map(UserEntity::getId)
                    .collect(Collectors.toList());
            dto.setUserIds(userIds);
        }

        // Set activity group ID if available
        if (session.getActivityGroupEntity() != null) {
            dto.setActivityGroupId(session.getActivityGroupEntity().getId());
        }

        // Set pricing ID if available
        if (session.getPricing() != null) {
            dto.setPricingId(session.getPricing().getId());
        }

        return dto;
    }

    //Används för att tilldela en användare till en session
    public AssignUsersToSessionResponse assignUsersToASession(AssignUsersDTO assignUsersDTO, Long sessionId) {
        AssignUsersToSessionResponse response = new AssignUsersToSessionResponse();
        try {
            SessionEntity sessionEntity = sessionRepository.findById(sessionId)
                    .orElseThrow(() -> new IllegalArgumentException("Session not found"));

            List<UserEntity> users = userEntityRepository.findAllById(assignUsersDTO.getUserIds());
            setUserData(users, sessionEntity);
            response.setSuccess(true);
            response.setMessage("Users assigned to session successfully!");
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage("Failed to assign users to session: " + e.getMessage());
        }
        return response;
    }

    //Används för att tilldela en användare till alla sessioner i en aktivitetsgrupp på en gång för att spara tid
    public AssignUsersToSessionResponse assignUsersToAllSessionsInAnActivityGroup(AssignUsersDTO assignUsersDTO, Long activityGroupId) {
        AssignUsersToSessionResponse response = new AssignUsersToSessionResponse();
        try {
            List<SessionEntity> sessions = sessionRepository.findByActivityGroupEntityId(activityGroupId);
            List<UserEntity> users = userEntityRepository.findAllById(assignUsersDTO.getUserIds());

            for (SessionEntity sessionEntity : sessions) {
                setUserData(users, sessionEntity);
            }
            response.setSuccess(true);
            response.setMessage("Users assigned to all sessions in the activity group successfully!");
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage("Failed to assign users to all sessions: " + e.getMessage());
        }
        return response;
    }

    //Används i assignUsersToASession och assignUsersToAllSessions
    private void setUserData(List<UserEntity> users, SessionEntity sessionEntity) {
        for (UserEntity user : users) {
            Attendance attendance = new Attendance();
            attendance.setUser(user);
            attendance.setSession(sessionEntity);
            attendance.setInvited(true); // Send email notification based on this
            attendance.setPresent(false); // Initially mark as not present
            attendanceRepository.save(attendance);
        }
    }

    //Används för att registrera närvaro för en användare genom att sätta isPresent in attendance objektet till true
    public CheckInUserResponse checkInUser(Long sessionId, Long userId) {
        CheckInUserResponse response = new CheckInUserResponse();
        try {
            Attendance attendance = attendanceRepository.findBySessionIdAndUserId(sessionId, userId)
                    .orElseThrow(() -> new IllegalArgumentException("Attendance record not found"));
            attendance.setPresent(true);
            attendanceRepository.save(attendance);
            response.setSuccess(true);
            response.setMessage("User checked in successfully");
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage("Failed to check in user: " + e.getMessage());
            // Log error here if necessary
        }
        return response;
    }

}
