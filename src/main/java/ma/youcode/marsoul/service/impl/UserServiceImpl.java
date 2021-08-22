package ma.youcode.marsoul.service.impl;

import ma.youcode.marsoul.config.mail.EmailSender;
import ma.youcode.marsoul.config.security.CustomUserDetails;
import ma.youcode.marsoul.config.security.jwt.JwtProvider;
import ma.youcode.marsoul.entity.*;
import ma.youcode.marsoul.exception.*;
import ma.youcode.marsoul.repository.RoleRepository;
import ma.youcode.marsoul.repository.UserRepository;
import ma.youcode.marsoul.dto.request.LoginRequest;
import ma.youcode.marsoul.dto.request.RefreshTokenRequest;
import ma.youcode.marsoul.dto.request.ResetPasswordRequest;
import ma.youcode.marsoul.service.PasswordTokenService;
import ma.youcode.marsoul.service.RefreshTokenService;
import ma.youcode.marsoul.service.UserService;
import ma.youcode.marsoul.service.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private VerificationTokenService verificationTokenService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private PasswordTokenService passwordTokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailSender emailSender;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotExistException("User does not exist"));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User saveUser(User user, Collection<String> roleNames) {
        Optional<User> user1 = userRepository.findByEmail(user.getEmail());
        Collection<Role> roles = new HashSet<>();
        if (user1.isPresent()) {
            throw new EmailExistException("Email already in use");
        }
        if (roleNames == null) {
            Role role = roleRepository.findByName("ROLE_USER");
            roles.add(role);
            user.setRoles(roles);
        } else {
            for (String roleName : roleNames) {
                Role role = roleRepository.findByName(roleName);
                if (role == null) {
                    throw new EntityNotExistException(roleName + " does not exist");
                }
                roles.add(role);
                user.setRoles(roles);
            }
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        // generate verification token for this user
        VerificationToken verificationToken = new VerificationToken(user);
        verificationTokenService.saveVerificationToken(verificationToken);
        // link that we want to send to user to verify his account
        String link = "http://localhost:8080/marsoul/api/v1/auth/signup/confirm/" + verificationToken.getToken();
        // create templateModel for thymeleaf template
        sendEmailToUser(user, link, "email-verification", "Email Verification");
        return user;
    }

    @Override
    public User updateUser(Long userId, User user) {
        User targetedUser = getUserById(userId);
        targetedUser.setFirstName(user.getFirstName());
        targetedUser.setLastName(user.getLastName());
        targetedUser.setPhone(user.getPhone());
        targetedUser.setImage(user.getImage());
        targetedUser.setEmail(user.getEmail());
        targetedUser.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(targetedUser);
    }

    @Override
    public void deleteUserById(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
        } else {
            throw new EntityNotExistException("User does not exist");
        }
    }

    @Override
    public void verifyAccount(String token) {
        VerificationToken verificationToken = verificationTokenService.getToken(token)
                .orElseThrow(() ->
                        new TokenNotFoundException("Token invalid"));

        if (verificationToken.getConfirmDate() != null) {
            throw new TokenConfirmedException("Email already confirmed");
        }

        LocalDateTime expiredAt = verificationToken.getExpireDate();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new TokenExpiredException("Token expired");
        }

        verificationTokenService.setConfirmedDate(token);
        userRepository.enableUser(verificationToken.getUser().getEmail());
    }

    @Override
    public String verifyRefreshToken(String token) {
        RefreshToken refreshToken = refreshTokenService.getToken(token)
                .orElseThrow(() ->
                        new TokenNotFoundException("Token does not found"));

        LocalDateTime expiredAt = refreshToken.getExpireDate();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new TokenExpiredException("Token was expired. Please make a new signing request");
        }
        CustomUserDetails customUserDetails = new CustomUserDetails(refreshToken.getUser());
        return jwtProvider.generateToken(customUserDetails);

    }

    @Override
    public String loginAccount(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        // to check the user is logged in or not
        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        // generate refresh token and save it in database
        RefreshToken refreshToken = new RefreshToken(userDetails.getUser());
        refreshTokenService.saveRefreshToken(refreshToken);
        // generate access token
        return jwtProvider.generateToken(userDetails);
    }

    @Override
    public void logoutAccount(RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.deleteToken(refreshTokenRequest.getRefreshToken());
    }

    @Override
    public void resetPassword(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new EmailNotExistException("Email does not exist"));
        PasswordToken passwordToken = new PasswordToken(user);
        passwordTokenService.savePasswordToken(passwordToken);
        // link that we want to send to user to reset password
        String link = "http://localhost:8080/marsoul/api/v1/auth/reset-password/" + passwordToken.getToken();
        // create templateModel for thymeleaf template
        sendEmailToUser(user, link, "reset-password", "Reset password");
    }

    @Override
    public void verifyResetPassword(String token) {
        PasswordToken passwordToken = passwordTokenService.getToken(token)
                .orElseThrow(() ->
                        new TokenNotFoundException("Token does not found"));

        if (passwordToken.getConfirmDate() != null) {
            throw new TokenConfirmedException("Password already confirmed");
        }

        LocalDateTime expiredAt = passwordToken.getExpireDate();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new TokenExpiredException("Token expired");
        }

        passwordTokenService.setConfirmedDate(token);
    }

    @Override
    public void updatePassword(ResetPasswordRequest resetPasswordRequest) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = principal.getUsername();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotExistException("User does not exist"));
        if (resetPasswordRequest.getPassword().equals(resetPasswordRequest.getConfirmPassword())) {
            user.setPassword(passwordEncoder.encode(resetPasswordRequest.getPassword()));
        } else {
            throw new PasswordNotMatchException("Password does not matches");
        }
    }

    private void sendEmailToUser(User user, String link, String thymeleafName, String emailSubject) {
        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("firstName", user.getFirstName());
        templateModel.put("registerDate", new Date());
        templateModel.put("link", link);
        templateModel.put("logo", "static/logo.png");
        emailSender.sendEmail(user.getEmail(), emailSubject, thymeleafName, templateModel);
    }

}
