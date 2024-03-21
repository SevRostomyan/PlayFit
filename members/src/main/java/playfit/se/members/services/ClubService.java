package playfit.se.members.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import playfit.se.members.DTOs.InfoClubDTO;
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


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClubService {

    private final ClubRepository clubRepository;
    private final UserEntityRepository userEntityRepository;
    private final UserEntityService userEntityService;
    private final AddressRepository addressRepository;
    private final EmailService emailService;


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
            userEntity.setCreatedAt(LocalDateTime.now());
            userEntityRepository.save(userEntity);
            String clubName = signUpClubDTO.getClubName();
            emailService.sendSimpleEmail(userEntity.getEmail(),
                    "Welcome to PlayFit",
                    "Welcome to PlayFit.");
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


    public ClubUpdateResponse updateClubInfo(Long clubId, InfoClubDTO infoClubDTO) {
        Optional<ClubEntity> clubEntityOptional = clubRepository.findById(clubId);
        ClubUpdateResponse response = new ClubUpdateResponse();
        if (clubEntityOptional.isPresent()) {
            ClubEntity clubEntity = clubEntityOptional.get();
            clubEntity.setClubName(infoClubDTO.getOrgName());
            clubEntity.setAddress(infoClubDTO.getAddress());
            clubEntity.setZipCode(infoClubDTO.getZipCode());
            clubEntity.setCity(infoClubDTO.getCity());
            clubEntity.setMobile(infoClubDTO.getMobile());

            clubRepository.save(clubEntity);
            response.setSuccess(true);
            response.setMessage("You have successfully updated the club.");
        } else {
            response.setSuccess(false);
            response.setMessage("Something went wrong, couldn't update club");

        }
        return response;
    }

    public List<InfoClubDTO> getAllInfoClubs() {
        List<ClubEntity> clubs = clubRepository.findAll();
        return clubs.stream().map(this::covertToClubInfoDTO).collect(Collectors.toList());
    }

    private InfoClubDTO covertToClubInfoDTO(ClubEntity clubEntity) {
        InfoClubDTO clubInfoDTO = new InfoClubDTO();
        clubInfoDTO.setOrgName(clubEntity.getClubName());
        clubInfoDTO.setAddress(clubEntity.getAddress());
        clubInfoDTO.setZipCode(clubEntity.getZipCode());
        clubInfoDTO.setCity(clubEntity.getCity());
        clubInfoDTO.setMobile(clubEntity.getMobile());
        return clubInfoDTO;
    }



}







