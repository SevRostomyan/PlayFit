package playfit.se.members.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import playfit.se.members.DTOs.SignInDTO;
import playfit.se.members.DTOs.SignUpUserEntityDTO;
import playfit.se.members.entities.AddressEntity;
import playfit.se.members.entities.ClubEntity;
import playfit.se.members.entities.RoleEntity;
import playfit.se.members.entities.UserEntity;
import playfit.se.members.enums.Role;
import playfit.se.members.repositories.ClubRepository;
import playfit.se.members.repositories.RoleRepository;
import playfit.se.members.repositories.UserEntityRepository;
import playfit.se.members.responses.UserLogInResponse;
import playfit.se.members.responses.UserRegistrationResponse;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserEntityService {
    final private UserEntityRepository userEntityRepository;
    private final RoleRepository roleRepository;
    final private ClubRepository clubRepository;

    public UserRegistrationResponse signUp(SignUpUserEntityDTO signUpUserEntityDTO) {
        ClubEntity existingClubEntity = clubRepository.findById(signUpUserEntityDTO.getOrgId())
                .orElseThrow(()-> new IllegalArgumentException("No organization found"));
        Optional<UserEntity> existingUserEntity = userEntityRepository.findUserByEmail(signUpUserEntityDTO.getEmail());
        UserRegistrationResponse response = new UserRegistrationResponse();

        if (existingUserEntity.isPresent()) {
            response.setSuccess(false);
            response.setMessage("You already have an account!");
        } else {
            AddressEntity addressEntity = new AddressEntity();
            addressEntity.setStreet(signUpUserEntityDTO.getAddressDTO().getStreet());
            addressEntity.setZipcode(signUpUserEntityDTO.getAddressDTO().getZipcode());
            addressEntity.setCity(signUpUserEntityDTO.getAddressDTO().getCity());

            UserEntity userEntity = getUserEntity(signUpUserEntityDTO, addressEntity);
            userEntity.setClubEntity(List.of(existingClubEntity));
            userEntityRepository.save(userEntity);
            clubRepository.save(existingClubEntity);

            RoleEntity role = new RoleEntity();
            role.setUser(userEntity);
            role.setClubId(existingClubEntity.getId());
            role.setRole(Role.STUDENT);
            role.setType(Role.STUDENT.name());
            roleRepository.save(role);

            response.setSuccess(true);
            response.setMessage("You have successfully created an account!");
        }
        return (response);
    }

    public UserEntity getUserEntity(SignUpUserEntityDTO signUpUserEntityDTO, AddressEntity addressEntity) {
        UserEntity userEntity = new UserEntity();

        userEntity.setEmail(signUpUserEntityDTO.getEmail());
        userEntity.setPassword(signUpUserEntityDTO.getPassword());
        userEntity.setFirstName(signUpUserEntityDTO.getFirstname());
        userEntity.setLastName(signUpUserEntityDTO.getLastname());
        userEntity.setPersonalNumber(signUpUserEntityDTO.getPersonalNumber());
        userEntity.setGender(signUpUserEntityDTO.getGender());
        userEntity.setMobile(signUpUserEntityDTO.getMobile());
        //userEntity.setOrgId(signUpDTO.getOrgId());
        userEntity.setAddressEntity(addressEntity);
        return userEntity;
    }

    public UserLogInResponse signIn(SignInDTO signInDTO) {
        UserEntity existingUser = userEntityRepository.findUserByEmail(signInDTO.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("No user found"));
        UserLogInResponse response = new UserLogInResponse();

        if (!existingUser.isAccountStatus()) {
            response.setSuccess(false);
            response.setMessage("Please verify your email to log in.");
        } else if(!Objects.equals(existingUser.getPassword(), signInDTO.getPassword())) {
            response.setSuccess(false);
            response.setMessage("Invalid email or password.");
        }else{
            existingUser.setLoginStatus(true);
            userEntityRepository.save(existingUser);
            response.setSuccess(true);
            response.setMessage("You have successfully logged in.");
        }


        return response;
    }

    public UserRegistrationResponse addClub(Long memberId, Long clubId) {
        UserEntity userEntity = userEntityRepository.findById(memberId).orElseThrow(()-> new IllegalArgumentException("No user found"));
        ClubEntity clubEntity = clubRepository.findById(clubId).orElseThrow(()-> new IllegalArgumentException("No club found"));

        userEntity.getClubEntity().add(clubEntity);
        userEntityRepository.save(userEntity);

        UserRegistrationResponse response = new UserRegistrationResponse();
        response.setSuccess(true);
        response.setMessage("You have successfully added a club!");
        return response;
    }
}
