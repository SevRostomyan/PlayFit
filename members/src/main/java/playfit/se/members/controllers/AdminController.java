package playfit.se.members.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import playfit.se.members.DTOs.EmailDTO;
import playfit.se.members.entities.UserEntity;
import playfit.se.members.repositories.UserEntityRepository;
import playfit.se.members.services.EmailService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admins")
@RequiredArgsConstructor
public class AdminController {
    private final EmailService emailService;
    private final UserEntityRepository userEntityRepository;

    @PostMapping("/send-single-email/{userId}")
    public ResponseEntity<String> sendSingleEmail(@PathVariable Long userId, @RequestBody EmailDTO emailDTO) {
        UserEntity existingUser = userEntityRepository.findById(userId).
                orElseThrow(() -> new RuntimeException("User not found"));
        String receiverEmail = existingUser.getEmail();
        String response = emailService.sendSimpleEmail(receiverEmail, emailDTO.getSubject(), emailDTO.getContent());
        return ResponseEntity.ok(response);
    }

    @PostMapping("send-multiple-email")
    public ResponseEntity<String> sendMultipleEmail(@RequestBody EmailDTO emailDTO) {
        List<String> users = userEntityRepository.findAll().stream().map(UserEntity::getEmail).toList();
        String response = emailService.sendMultipleSimpleEmail(users, emailDTO.getSubject(), emailDTO.getContent());
        return ResponseEntity.ok(response);
    }
}
