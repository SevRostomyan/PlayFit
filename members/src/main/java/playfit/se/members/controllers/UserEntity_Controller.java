package playfit.se.members.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import playfit.se.members.DTOs.UserEntity_DTO;
import playfit.se.members.repositories.UserEntity_Repository;
import playfit.se.members.responses.User_Registration_Response;
import playfit.se.members.services.UserEntity_Service;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserEntity_Controller {

    final private UserEntity_Repository userEntity_repository;
    final private UserEntity_Service userEntity_service;

    @PostMapping()
    public ResponseEntity<String> sign_up(@RequestBody UserEntity_DTO userEntity_dto) {
      User_Registration_Response response=  userEntity_service.sign_up(userEntity_dto);
        if (response.isSuccess()) {
            // vi måste implementera notification (send an email)
            return ResponseEntity.ok(response.getMessage());
        } else {
            return ResponseEntity.badRequest().body(response.getMessage());
        }
    }
}
