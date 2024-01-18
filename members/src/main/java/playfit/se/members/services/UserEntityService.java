package playfit.se.members.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import playfit.se.members.DTOs.SignUpDTO;
import playfit.se.members.entity.AddressEntity;
import playfit.se.members.entity.UserEntity;
import playfit.se.members.repositories.UserEntityRepository;
import playfit.se.members.responses.UserRegistrationResponse;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserEntityService {
    final private UserEntityRepository userEntityRepository;

    public UserRegistrationResponse signUp(SignUpDTO signUpDTO) {
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

            userEntityRepository.save(userEntity);
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
        userEntity.setOrgId(signUpDTO.getOrgId());
        userEntity.setAddressEntity(addressEntity);
        return userEntity;
    }
}
