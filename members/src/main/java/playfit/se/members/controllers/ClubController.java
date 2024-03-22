package playfit.se.members.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import playfit.se.members.DTOs.InfoClubDTO;
import playfit.se.members.DTOs.SignUpClubDTO;
import playfit.se.members.responses.ClubRegistrationResponse;
import playfit.se.members.responses.ClubUpdateResponse;
import playfit.se.members.services.ClubService;

import java.util.List;

@RequestMapping("/api/v1/clubs")
@RestController
@RequiredArgsConstructor
@CrossOrigin
public class ClubController {


    private final ClubService clubService;



    @PostMapping("/create-club")
    public ResponseEntity<String> createClub(@RequestBody SignUpClubDTO signUpClubDTO) {
        ClubRegistrationResponse response = clubService.createClub(signUpClubDTO);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response.getMessage());
        } else {
            return ResponseEntity.badRequest().body(response.getMessage());
        }
    }

    @PostMapping("/update-club-info/{clubId}")
    public ResponseEntity<String> updateClubInfo(@PathVariable Long clubId, @RequestBody InfoClubDTO infoClubDTO) {
        ClubUpdateResponse response = clubService.updateClubInfo(clubId, infoClubDTO);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response.getMessage());
        } else {
            return ResponseEntity.badRequest().body(response.getMessage());
        }
    }
       @GetMapping("/all")
        public ResponseEntity<List<InfoClubDTO>> getAllClubsInfo() {
            List<InfoClubDTO> InfoclubDTOS = clubService.getAllInfoClubs();
            return ResponseEntity.ok(InfoclubDTOS);

        }




    }
