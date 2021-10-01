package ma.youcode.marsoul.config.security;

import ma.youcode.marsoul.entity.User;
import ma.youcode.marsoul.exception.ConfirmEmailException;
import ma.youcode.marsoul.exception.EntityNotExistException;
import ma.youcode.marsoul.exception.ExceedLoginAttemptsException;
import ma.youcode.marsoul.repository.UserRepository;
import ma.youcode.marsoul.service.LoginAttemptService;
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

    @Autowired
    private LoginAttemptService loginAttemptService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userByEmail = userRepository.findByEmail(email);

        if (!userByEmail.isPresent()) {
            throw new EntityNotExistException("Email not found");
        } else {
            User user = userByEmail.get();

            if (!user.isEnabled()) {
                throw new ConfirmEmailException("Please check your email and confirm your account");
            }
            if (!user.isActivated()) {
                throw new ExceedLoginAttemptsException("Your account has been locked. Please contact administration");
            }
            if (loginAttemptService.hasExceedMaxAttempts(user.getEmail())) {
                user.setActivated(false);
                userRepository.save(user);
                throw new ExceedLoginAttemptsException("Your account has been locked. Please contact administration");
            }
            return new CustomUserDetails(user);
        }
    }
}

