package playfit.se.members.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import playfit.se.members.DTOs.UserEntity_DTO;
import playfit.se.members.entity.Address_Entity;
import playfit.se.members.entity.User_Entity;
import playfit.se.members.repositories.Address_Repository;
import playfit.se.members.repositories.UserEntity_Repository;
import playfit.se.members.responses.User_Registration_Response;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserEntity_Service {
    final private UserEntity_Repository userEntity_repository;
    final private Address_Repository addressRepository;

    public User_Registration_Response sign_up(UserEntity_DTO userEntity_dto) {
        Optional<User_Entity> existing_userEntity = userEntity_repository.findUserByEmail(userEntity_dto.getEmail());
        User_Registration_Response response = new User_Registration_Response();

        if (existing_userEntity.isPresent()) {
            response.setSuccess(false);
            response.setMessage("You already have an account!");
        } else {
            Address_Entity addressEntity = new Address_Entity();
            addressEntity.setStreet(userEntity_dto.getAddressEntity().getStreet());
            addressEntity.setZipcode(userEntity_dto.getAddressEntity().getZipcode());
            addressEntity.setCity(userEntity_dto.getAddressEntity().getCity());

            User_Entity userEntity = getUserEntity(userEntity_dto, addressEntity);

            userEntity_repository.save(userEntity);

        }
        return (response);
    }

    private static User_Entity getUserEntity(UserEntity_DTO userEntity_dto, Address_Entity addressEntity) {
        User_Entity userEntity = new User_Entity();

        userEntity.setEmail(userEntity_dto.getEmail());
        userEntity.setPassword(userEntity_dto.getPassword());
        userEntity.setFirstname(userEntity_dto.getFirstname());
        userEntity.setLastname(userEntity_dto.getLastname());
        userEntity.setPersonal_number(userEntity_dto.getPersonal_number());
        userEntity.setGender(userEntity_dto.getGender());
        userEntity.setMobile(userEntity_dto.getMobile());
        userEntity.setAddressEntity(addressEntity);
        return userEntity;
    }
}
