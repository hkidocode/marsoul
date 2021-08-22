package ma.youcode.marsoul.config.security;

import ma.youcode.marsoul.entity.User;
import ma.youcode.marsoul.exception.EntityNotExistException;
import ma.youcode.marsoul.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userByEmail = userRepository.findByEmail(email);
        if (!userByEmail.isPresent()) {
            throw new EntityNotExistException("User does not exist");
        }
        return new CustomUserDetails(userByEmail.get());

    }

}
