package playfit.se.members.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import playfit.se.members.repositories.UserEntityRepository;
import playfit.se.members.services.UserService;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserEntityRepository userEntityRepository;
    @Override
    public UserDetailsService userDetailsService(){
        return new UserDetailsService(){
            @Override
            public UserDetails loadUserByUsername(String username) {
                return userEntityRepository.findUserByEmail(username)
                        .orElseThrow(()-> new UsernameNotFoundException("User not found"));
            }
        };
    }
}
