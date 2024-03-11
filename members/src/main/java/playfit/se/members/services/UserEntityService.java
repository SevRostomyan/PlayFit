package playfit.se.members.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import playfit.se.members.DTOs.SignInDTO;
import playfit.se.members.DTOs.SignUpUserEntityDTO;
import playfit.se.members.entities.AddressEntity;
import playfit.se.members.entities.ClubEntity;
import playfit.se.members.entities.UserEntity;
import playfit.se.members.enums.Role;
import playfit.se.members.repositories.AddressRepository;
import playfit.se.members.repositories.ClubRepository;
import playfit.se.members.repositories.TokenRepository;
import playfit.se.members.repositories.UserEntityRepository;
import playfit.se.members.responses.UserLogInResponse;
import playfit.se.members.responses.UserRegistrationResponse;
import playfit.se.members.token.Token;
import playfit.se.members.token.TokenType;

import java.util.Optional;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class UserEntityService {
    private final UserEntityRepository userEntityRepository;
    private final ClubRepository clubRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final AddressRepository addressRepository;
    private final TokenRepository tokenRepository;
    private final EmailService emailService;


    public UserRegistrationResponse signUp(Long clubId, SignUpUserEntityDTO signUpUserEntityDTO) {
        ClubEntity existingClubEntity = clubRepository.findById(clubId)
                .orElseThrow(() -> new IllegalArgumentException("No club found"));
        Optional<UserEntity> existingUserEntity = (userEntityRepository.findByEmailAndClubId(signUpUserEntityDTO.getEmail(), clubId));
        UserRegistrationResponse response = new UserRegistrationResponse();

        if (existingUserEntity.isPresent()) {
            response.setSuccess(false);
            response.setMessage("You already have an account in this club!");
        } else {
            AddressEntity addressEntity = new AddressEntity();
            addressEntity.setStreet(signUpUserEntityDTO.getAddressDTO().getStreet());
            addressEntity.setZipcode(signUpUserEntityDTO.getAddressDTO().getZipcode());
            addressEntity.setCity(signUpUserEntityDTO.getAddressDTO().getCity());
            addressRepository.save(addressEntity);
            UserEntity userEntity = getUserEntity(signUpUserEntityDTO, addressEntity);
            userEntity.setClubEntity(existingClubEntity);
            userEntity.setRoles(Set.of(Role.USER));
            userEntityRepository.save(userEntity);
            clubRepository.save(existingClubEntity);
            String clubName = existingClubEntity.getClubName();
            emailService.sendSimpleEmail(signUpUserEntityDTO.getEmail(),
                    "Welcome to " + clubName,
                    "Welcome to " + clubName + "!");
            response.setSuccess(true);
            response.setMessage("You have successfully created an account in " + clubName + "!");
        }
        return (response);
    }

    public UserEntity getUserEntity(SignUpUserEntityDTO signUpUserEntityDTO, AddressEntity addressEntity) {
        UserEntity userEntity = new UserEntity();

        userEntity.setEmail(signUpUserEntityDTO.getEmail());
        userEntity.setPassword(passwordEncoder.encode(signUpUserEntityDTO.getPassword()));
        userEntity.setFirstName(signUpUserEntityDTO.getFirstname());
        userEntity.setLastName(signUpUserEntityDTO.getLastname());
        userEntity.setPersonalNumber(signUpUserEntityDTO.getPersonalNumber());
        userEntity.setGender(signUpUserEntityDTO.getGender());
        userEntity.setMobile(signUpUserEntityDTO.getMobile());
        //userEntity.setOrgId(signUpDTO.getOrgId());
        userEntity.setAddressEntity(addressEntity);
        return userEntity;
    }

    // we bring clubId from a list that contains all names of clubs that exist in the system( frontend).
    public UserLogInResponse signIn(Long clubId, SignInDTO signInDTO) {
        var existingUser = userEntityRepository.findByEmailAndClubId(signInDTO.getEmail(), clubId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password!"));

        UserLogInResponse response = new UserLogInResponse();

        if (!existingUser.isAccountStatus()) {
            response.setSuccess(false);
            response.setMessage("Please verify your email to log in.");
        } else {
            try {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInDTO.getEmail(), signInDTO.getPassword()));
                existingUser.setLoginStatus(true);
                var savedUser = userEntityRepository.save(existingUser);
                revokeAllUserTokens(existingUser);
                var jwt = jwtService.generateToken(existingUser);
                var token = Token.builder()
                        .userEntity(savedUser)
                        .token(jwt)
                        .tokenType(TokenType.BEARER)
                        .revoked(false)
                        .expired(false)
                        .build();
                tokenRepository.save(token);
                response.setSuccess(true);
                response.setMessage("You have successfully logged in.");
                response.setAccessToken(jwt);
            } catch (AuthenticationException e) {
                response.setSuccess(false);
                response.setMessage("Authentication failed.");
            }
        }
        return response;
    }

    private void revokeAllUserTokens(UserEntity userEntity) {
        var validUserTokens = tokenRepository.findAllValidTokensByUserId(userEntity.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void updateUserRoles(Long userId, Set<Role> newRoles) {
        // Fetch the user entity from the database
        UserEntity user = userEntityRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        // Update the roles of the user entity
        user.setRoles(newRoles);

        // Save the updated user entity back to the database
        userEntityRepository.save(user);
    }
}