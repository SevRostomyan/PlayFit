package playfit.se.members.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import playfit.se.members.DTOs.SignInDTO;
import playfit.se.members.DTOs.SignUpUserEntityDTO;
import playfit.se.members.responses.UserLogInResponse;
import playfit.se.members.responses.UserRegistrationResponse;
import playfit.se.members.services.UserEntityService;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserEntityController {

    final private UserEntityService userEntityService;

    @PostMapping("/sign-up")
    public ResponseEntity<String> signUp(@RequestBody SignUpUserEntityDTO signUpUserEntityDTO) {
        UserRegistrationResponse response = userEntityService.signUp(signUpUserEntityDTO);
        if (response.isSuccess()) {
            // vi måste implementera notification (send an email)
            return ResponseEntity.ok(response.getMessage());
        } else {
            return ResponseEntity.badRequest().body(response.getMessage());
        }
    }
    @PutMapping("/add-club/{memberId}/{clubId}")
    public ResponseEntity<String> addClub(@PathVariable Long memberId, @PathVariable Long clubId){
        UserRegistrationResponse response = userEntityService.addClub(memberId, clubId);
        if (response.isSuccess()) {
            // vi måste implementera notification (send an email)
            return ResponseEntity.ok(response.getMessage());
        } else {
            return ResponseEntity.badRequest().body(response.getMessage());
        }
    }

    // Vi behöver att lägga till förutsättningen att man har en godkänd konto.
    @PostMapping("sign-in")
    public ResponseEntity<String> signIn(@RequestBody SignInDTO signInDTO) {
        UserLogInResponse response = userEntityService.signIn(signInDTO);
        if (response.getMessage() !=null) {
            // vi måste implementera notification (send an email)
            return ResponseEntity.ok(response.getMessage());
        }
        else{
            return ResponseEntity.badRequest().body("Something went wrong");
        }
    }
}
