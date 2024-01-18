package playfit.se.members.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import playfit.se.members.DTOs.SignUpDTO;
import playfit.se.members.responses.UserRegistrationResponse;
import playfit.se.members.services.UserEntityService;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserEntityController {

    final private UserEntityService userEntityService;

    @PostMapping()
    public ResponseEntity<String> signUp(@RequestBody SignUpDTO signUpDTO) {
      UserRegistrationResponse response=  userEntityService.signUp(signUpDTO);
        if (response.isSuccess()) {
            // vi måste implementera notification (send an email)
            return ResponseEntity.ok(response.getMessage());
        } else {
            return ResponseEntity.badRequest().body(response.getMessage());
        }
    }

}
