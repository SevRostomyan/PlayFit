package playfit.se.members.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import playfit.se.members.DTOs.SignInDTO;
import playfit.se.members.DTOs.SignUpUserEntityDTO;
import playfit.se.members.responses.UserLogInResponse;
import playfit.se.members.responses.UserRegistrationResponse;
import playfit.se.members.services.EmailService;
import playfit.se.members.services.UserEntityService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserEntityController {

    final private UserEntityService userEntityService;
    final private EmailService emailService;

    @PostMapping("/sign-up/{clubId}")
    public ResponseEntity<String> signUp(@PathVariable Long clubId, @RequestBody SignUpUserEntityDTO signUpUserEntityDTO) {
        UserRegistrationResponse response = userEntityService.signUp(clubId, signUpUserEntityDTO);
        if (response.isSuccess()) {
            // vi måste implementera notification (send an email)
            return ResponseEntity.ok(response.getMessage());
        } else {
            return ResponseEntity.badRequest().body(response.getMessage());
        }
    }

    // Vi behöver att lägga till förutsättningen att man har en godkänd konto.
    @PostMapping("/sign-in/{clubId}")
    public ResponseEntity<String> signIn(@PathVariable Long clubId, @RequestBody SignInDTO signInDTO) {
        UserLogInResponse response = userEntityService.signIn(clubId, signInDTO);
        if (response.getMessage() != null) {
            // vi måste implementera notification (send an email)
            return ResponseEntity.ok(response.getMessage());
        } else {
            return ResponseEntity.badRequest().body("Something went wrong");
        }
    }

    @PostMapping("/send-simple-email")
    public String sendSimpleEmail(@RequestParam String to, @RequestParam String subject, @RequestParam String content) {
        return emailService.sendSimpleEmail(to, subject, content);
    }

    @PostMapping("send-multiple-simple-Email")
    public String sendMultipleSimpleEmail(@RequestParam List<String> toList,
                                          @RequestParam String subject,
                                          @RequestParam String content) {
        return emailService.sendMultipleSimpleEmail(toList, subject, content);
    }
}
