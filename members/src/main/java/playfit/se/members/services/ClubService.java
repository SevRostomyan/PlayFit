package playfit.se.members.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import playfit.se.members.DTOs.SignUpClubDTO;

import playfit.se.members.entities.AddressEntity;

import playfit.se.members.entities.ClubEntity;
import playfit.se.members.entities.RoleEntity;
import playfit.se.members.entities.UserEntity;

import playfit.se.members.enums.Role;
import playfit.se.members.repositories.ClubRepository;
import playfit.se.members.repositories.RoleRepository;
import playfit.se.members.repositories.UserEntityRepository;
import playfit.se.members.responses.ClubRegistrationResponse;
import playfit.se.members.responses.ClubUpdateResponse;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClubService {

    private final ClubRepository clubRepository;
    private final RoleRepository roleRepository;
    private final UserEntityRepository userEntityRepository;
    private final UserEntityService userEntityService;

    public ClubRegistrationResponse createClub(SignUpClubDTO signUpClubDTO) {
        Optional<ClubEntity> existingOrganizationClubEntity = clubRepository.findByOrgNr(signUpClubDTO.getOrgNr());
        Optional<UserEntity> existingUserEntity = userEntityRepository.findUserByEmail(signUpClubDTO.getRepresentative().getEmail());
        RoleEntity role = new RoleEntity();
        ClubRegistrationResponse response = new ClubRegistrationResponse();
        if (existingOrganizationClubEntity.isPresent()) {
            response.setSuccess(false);
            response.setMessage("There is already an organization with this organization number");
        } else if (existingUserEntity.isPresent()) {
            UserEntity userEntity = existingUserEntity.get();
            ClubEntity clubEntity = getOrganizationClubEntity(signUpClubDTO);
            clubRepository.save(clubEntity);
            existingUserEntity.get().getClubEntity().add(clubEntity);
            setRoll(role, response, clubEntity, userEntity);
        }else {
            ClubEntity clubEntity = getOrganizationClubEntity(signUpClubDTO);
            clubRepository.save(clubEntity);
            AddressEntity addressEntity = new AddressEntity();
            addressEntity.setStreet(signUpClubDTO.getRepresentativeAddress().getStreet());
            addressEntity.setZipcode(signUpClubDTO.getRepresentativeAddress().getZipcode());
            addressEntity.setCity(signUpClubDTO.getRepresentativeAddress().getCity());

            UserEntity userEntity = userEntityService.getUserEntity(signUpClubDTO.getRepresentative(), addressEntity);
            userEntity.setClubEntity(List.of(clubEntity));
            setRoll(role, response, clubEntity, userEntity);
        }
        return (response);
    }

    private void setRoll(RoleEntity role, ClubRegistrationResponse response, ClubEntity clubEntity, UserEntity userEntity) {
        userEntityRepository.save(userEntity);
        role.setUser(userEntity);
        role.setClubId(clubEntity.getId());
        role.setRole(Role.REPRESENTATIVE);
        role.setType(Role.REPRESENTATIVE.name());
        roleRepository.save(role);
        response.setSuccess(true);
        response.setMessage("You have successfully created a new club account!");
    }

    private static ClubEntity getOrganizationClubEntity(SignUpClubDTO signUpClubDTO) {
        ClubEntity clubEntity = new ClubEntity();
        clubEntity.setOrgNr(signUpClubDTO.getOrgNr());
        clubEntity.setAddress(signUpClubDTO.getAddress());
        clubEntity.setZipCode(signUpClubDTO.getZipCode());
        clubEntity.setCity(signUpClubDTO.getCity());
        clubEntity.setMobile(signUpClubDTO.getMobile());
        clubEntity.setOrgName(signUpClubDTO.getOrgName());
        return clubEntity;
    }

}