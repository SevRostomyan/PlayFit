package playfit.se.members.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import playfit.se.members.DTOs.SignUpClubDTO;
import playfit.se.members.responses.ClubRegistrationResponse;
import playfit.se.members.services.ClubService;

@RequestMapping("/api/v1/clubs")
@RestController
@RequiredArgsConstructor
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

}