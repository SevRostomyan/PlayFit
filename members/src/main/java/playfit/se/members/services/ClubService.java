package playfit.se.members.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import playfit.se.members.DTOs.SignUpClubDTO;
import playfit.se.members.entities.AddressEntity;
import playfit.se.members.entities.ClubEntity;
import playfit.se.members.entities.UserEntity;
import playfit.se.members.enums.Role;
import playfit.se.members.repositories.AddressRepository;
import playfit.se.members.repositories.ClubRepository;
import playfit.se.members.repositories.UserEntityRepository;
import playfit.se.members.responses.ClubRegistrationResponse;
import playfit.se.members.responses.ClubUpdateResponse;


import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ClubService {

    private final ClubRepository clubRepository;
    private final UserEntityRepository userEntityRepository;
    private final UserEntityService userEntityService;
    private final AddressRepository addressRepository;

    public ClubRegistrationResponse createClub(SignUpClubDTO signUpClubDTO) {
        Optional<ClubEntity> existingClubEntity = clubRepository.findByOrgNr(signUpClubDTO.getOrgNr());
        ClubRegistrationResponse response = new ClubRegistrationResponse();
        if (existingClubEntity.isPresent()) {
            response.setSuccess(false);
            response.setMessage("There is already an organization with this organization number");

        } else {
            ClubEntity clubEntity = getClubEntity(signUpClubDTO);
            clubRepository.save(clubEntity);
            AddressEntity addressEntity = new AddressEntity();
            addressEntity.setStreet(signUpClubDTO.getRepresentative().getAddressDTO().getStreet());
            addressEntity.setZipcode(signUpClubDTO.getRepresentative().getAddressDTO().getZipcode());
            addressEntity.setCity(signUpClubDTO.getRepresentative().getAddressDTO().getCity());
            addressRepository.save(addressEntity);
            UserEntity userEntity = userEntityService.getUserEntity(signUpClubDTO.getRepresentative(), addressEntity);
            userEntity.setClubEntity(clubEntity);
            userEntity.setRoles(Set.of(Role.REPRESENTATIVE));
            userEntityRepository.save(userEntity);
            String clubName = signUpClubDTO.getClubName();
            response.setSuccess(true);
            response.setMessage("You have successfully created a club named " + clubName + "!");
        }
        return (response);
    }

    private static ClubEntity getClubEntity(SignUpClubDTO signUpClubDTO) {
        ClubEntity clubEntity = new ClubEntity();
        clubEntity.setOrgNr(signUpClubDTO.getOrgNr());
        clubEntity.setAddress(signUpClubDTO.getAddress());
        clubEntity.setZipCode(signUpClubDTO.getZipCode());
        clubEntity.setCity(signUpClubDTO.getCity());
        clubEntity.setMobile(signUpClubDTO.getMobile());
        clubEntity.setClubName(signUpClubDTO.getClubName());
        return clubEntity;
    }
}