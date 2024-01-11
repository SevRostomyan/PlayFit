package playfit.se.members.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import playfit.se.members.DTOs.UserEntityDTO;
import playfit.se.members.entity.AddressEntity;
import playfit.se.members.entity.UserEntity;
import playfit.se.members.repositories.UserEntityRepository;
import playfit.se.members.responses.UserRegistrationResponse;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserEntityService {
    final private UserEntityRepository userEntityRepository;

    public UserRegistrationResponse signUp(UserEntityDTO userEntityDTO) {
        Optional<UserEntity> existingUserEntity = userEntityRepository.findUserByEmail(userEntityDTO.getEmail());
        UserRegistrationResponse response = new UserRegistrationResponse();

        if (existingUserEntity.isPresent()) {
            response.setSuccess(false);
            response.setMessage("You already have an account!");
        } else {
            AddressEntity addressEntity = new AddressEntity();
            addressEntity.setStreet(userEntityDTO.getAddressEntity().getStreet());
            addressEntity.setZipcode(userEntityDTO.getAddressEntity().getZipcode());
            addressEntity.setCity(userEntityDTO.getAddressEntity().getCity());

            UserEntity userEntity = getUserEntity(userEntityDTO, addressEntity);

            userEntityRepository.save(userEntity);
            response.setSuccess(true);
            response.setMessage("You have successfully created an account!");
        }
        return (response);
    }

    private static UserEntity getUserEntity(UserEntityDTO userEntityDTO, AddressEntity addressEntity) {
        UserEntity userEntity = new UserEntity();

        userEntity.setEmail(userEntityDTO.getEmail());
        userEntity.setPassword(userEntityDTO.getPassword());
        userEntity.setFirstname(userEntityDTO.getFirstname());
        userEntity.setLastname(userEntityDTO.getLastname());
        userEntity.setPersonalNumber(userEntityDTO.getPersonalNumber());
        userEntity.setGender(userEntityDTO.getGender());
        userEntity.setMobile(userEntityDTO.getMobile());
        userEntity.setAddressEntity(addressEntity);
        return userEntity;
    }
}
