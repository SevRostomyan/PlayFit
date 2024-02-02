package playfit.se.members.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import playfit.se.members.DTOs.SignInDTO;
import playfit.se.members.DTOs.SignUpDTO;
import playfit.se.members.entities.AddressEntity;
import playfit.se.members.entities.OrganizationClubEntity;
import playfit.se.members.entities.UserEntity;
import playfit.se.members.repositories.OrganizationRepository;
import playfit.se.members.repositories.UserEntityRepository;
import playfit.se.members.responses.UserLogInResponse;
import playfit.se.members.responses.UserRegistrationResponse;

import java.util.Objects;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserEntityService {
    final private UserEntityRepository userEntityRepository;
    final private OrganizationRepository organizationRepository;

    public UserRegistrationResponse signUp(SignUpDTO signUpDTO) {
       OrganizationClubEntity existingOrganizationClubEntity = organizationRepository.findById(signUpDTO.getOrgId())
               .orElseThrow(()-> new IllegalArgumentException("No organization found"));
        Optional<UserEntity> existingUserEntity = userEntityRepository.findUserByEmail(signUpDTO.getEmail());
        UserRegistrationResponse response = new UserRegistrationResponse();

        if (existingUserEntity.isPresent()) {
            response.setSuccess(false);
            response.setMessage("You already have an account!");
        } else {
            AddressEntity addressEntity = new AddressEntity();
            addressEntity.setStreet(signUpDTO.getAddressEntity().getStreet());
            addressEntity.setZipcode(signUpDTO.getAddressEntity().getZipcode());
            addressEntity.setCity(signUpDTO.getAddressEntity().getCity());

            UserEntity userEntity = getUserEntity(signUpDTO, addressEntity);
            userEntity.setOrganizationClubEntity(existingOrganizationClubEntity);
            userEntityRepository.save(userEntity);
            organizationRepository.save(existingOrganizationClubEntity);
            response.setSuccess(true);
            response.setMessage("You have successfully created an account!");
        }
        return (response);
    }

    private static UserEntity getUserEntity(SignUpDTO signUpDTO, AddressEntity addressEntity) {
        UserEntity userEntity = new UserEntity();

        userEntity.setEmail(signUpDTO.getEmail());
        userEntity.setPassword(signUpDTO.getPassword());
        userEntity.setFirstName(signUpDTO.getFirstname());
        userEntity.setLastName(signUpDTO.getLastname());
        userEntity.setPersonalNumber(signUpDTO.getPersonalNumber());
        userEntity.setGender(signUpDTO.getGender());
        userEntity.setMobile(signUpDTO.getMobile());
//        userEntity.setOrgId(signUpDTO.getOrgId());
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
}
