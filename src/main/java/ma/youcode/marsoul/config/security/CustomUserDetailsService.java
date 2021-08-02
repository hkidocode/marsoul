package ma.youcode.marsoul.config.security;

import ma.youcode.marsoul.entity.User;
import ma.youcode.marsoul.exception.UserNotExistException;
import ma.youcode.marsoul.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userByEmail = userRepository.findUserByEmail(email);
        if (!userByEmail.isPresent()) {
            throw new UserNotExistException("User does not exist");
        }
        return new CustomUserDetails(userByEmail.get());

    }

}
