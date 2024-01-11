package playfit.se.members.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import playfit.se.members.DTOs.UserEntityDTO;
import playfit.se.members.responses.UserRegistrationResponse;
import playfit.se.members.services.UserEntityService;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserEntityController {

    final private UserEntityService userEntityService;

    @PostMapping()
    public ResponseEntity<String> signUp(@RequestBody UserEntityDTO userEntityDTO) {
      UserRegistrationResponse response=  userEntityService.signUp(userEntityDTO);
        if (response.isSuccess()) {
            // vi måste implementera notification (send an email)
            return ResponseEntity.ok(response.getMessage());
        } else {
            return ResponseEntity.badRequest().body(response.getMessage());
        }
    }
}
