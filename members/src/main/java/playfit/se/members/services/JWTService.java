package playfit.se.members.services;

import org.springframework.security.core.userdetails.UserDetails;

public interface JWTService {

    String extractUserName(String token);
    String generateToken(UserDetails userDetails);
}
