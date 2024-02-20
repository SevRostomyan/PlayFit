package playfit.se.members.services;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import playfit.se.members.entities.UserEntity;
import playfit.se.members.repositories.TokenRepository;
import playfit.se.members.repositories.UserEntityRepository;
import playfit.se.members.token.Token;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final TokenRepository tokenRepository;
    private final UserEntityRepository userEntityRepository;
    private final JWTService jwtService;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            try {
                response.getWriter().write("Invalid or missing authorization token");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return;
        }
        jwt = authHeader.substring(7);
        Token storedToken = tokenRepository.findByToken(jwt).orElse(null);
        if (storedToken != null) {
            if (storedToken.isExpired() || storedToken.isRevoked()) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                try {
                    response.getWriter().write("The token is expired or revoked");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                String userEmail = jwtService.extractUserName(jwt);
                Optional<UserEntity> userEntityOptional = userEntityRepository.findUserByEmail(userEmail);
                if (userEntityOptional.isPresent()) {
                    UserEntity user = userEntityOptional.get();
                    user.setLoginStatus(false);
                    userEntityRepository.save(user);
                    storedToken.setExpired(true);
                    storedToken.setRevoked(true);
                    tokenRepository.save(storedToken);
                    try {
                        response.getWriter().write("You have logged out!");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

        } else {
            try {
                response.getWriter().write("No token is found");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
