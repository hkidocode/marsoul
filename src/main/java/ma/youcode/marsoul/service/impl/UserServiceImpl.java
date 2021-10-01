package ma.youcode.marsoul.service.impl;

import ma.youcode.marsoul.config.mail.EmailSender;
import ma.youcode.marsoul.config.security.CustomUserDetails;
import ma.youcode.marsoul.config.security.jwt.JwtProvider;
import ma.youcode.marsoul.constant.FileConstant;
import ma.youcode.marsoul.dto.request.LoginRequest;
import ma.youcode.marsoul.dto.request.ResetPasswordRequest;
import ma.youcode.marsoul.entity.*;
import ma.youcode.marsoul.exception.*;
import ma.youcode.marsoul.repository.RoleRepository;
import ma.youcode.marsoul.repository.UserRepository;
import ma.youcode.marsoul.service.PasswordTokenService;
import ma.youcode.marsoul.service.RefreshTokenService;
import ma.youcode.marsoul.service.UserService;
import ma.youcode.marsoul.service.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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

    private User user;

    private boolean passwordTokenVerified = false;

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotExistException("User does not exist"));
    }

    @Override
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
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
                Role role = roleRepository.findByName("ROLE_" + roleName.toUpperCase());
                if (role == null) {
                    throw new EntityNotExistException("Role " + roleName + " does not exist");
                }
                roles.add(role);
                user.setRoles(roles);
            }
        }

        // to show the password error message
        if (user.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        user.setImage(getTemporaryProfileImageUrl(user.getFirstName(), user.getLastName()));
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
        targetedUser.setImage(setProfileImageUrl(targetedUser.getFirstName(), targetedUser.getLastName()));
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
    public User updateProfileImage(Long id, MultipartFile image) throws IOException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EmailNotExistException("Email does not exist"));
        saveProfileImage(user, image);
        return user;
    }

    @Override
    public void verifyAccount(String token) {
        VerificationToken verificationToken = verificationTokenService.getToken(token)
                .orElseThrow(() ->
                        new TokenNotFoundException("Invalid token"));

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
                        new TokenNotFoundException("Invalid token"));

        LocalDateTime expiredAt = refreshToken.getExpireDate();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new TokenExpiredException("Token was expired. Please make a new signing request");
        }
        CustomUserDetails customUserDetails = new CustomUserDetails(refreshToken.getUser());
        return jwtProvider.generateToken(customUserDetails);

    }

    @Override
    public HttpHeaders loginAccount(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        // to check the user is logged in or not
        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();
        // generate refresh token and save it to user in database
        RefreshToken refreshToken = new RefreshToken(user);
        refreshTokenService.saveRefreshToken(refreshToken);
        // set last login date
        user.setLastLoginDate(LocalDateTime.now());
        // generate access token
        return getJwtHeader(userDetails);
    }

    @Override
    public void logoutAccount(String token) {
        RefreshToken refreshToken = refreshTokenService.getToken(token).orElseThrow(() ->
                new TokenNotFoundException("Invalid token"));
        refreshTokenService.deleteToken(refreshToken.getUser());
    }

    @Override
    public void sendResetPassword(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new EmailNotExistException("Email does not exist"));
        PasswordToken passwordToken = new PasswordToken(user);
        passwordTokenService.savePasswordToken(passwordToken);
        // link that we want to send to user to reset password
        String link = "http://localhost:8080/marsoul/api/v1/auth/reset-password/" + passwordToken.getToken();
        // create templateModel for thymeleaf template
        sendEmailToUser(user, link, "reset-password", "Reset password");
        setUser(user);
    }

    @Override
    public void updatePassword(ResetPasswordRequest resetPasswordRequest, String token) {
        User user = getUser();

        if (!passwordTokenVerified) {
            verifyResetPassword(token);
        }

        if (resetPasswordRequest.getPassword().equals(resetPasswordRequest.getConfirmPassword())) {
            if (passwordEncoder.matches(resetPasswordRequest.getPassword(), user.getPassword())) {
                throw new PasswordAlreadyUsedException("Password already used");
            }
            user.setPassword(passwordEncoder.encode(resetPasswordRequest.getPassword()));
            userRepository.save(user);
            // delete refresh token of that user
            refreshTokenService.deleteToken(user);
        } else {
            throw new PasswordNotMatchException("Password does not matches");
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private void verifyResetPassword(String token) {

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
        this.passwordTokenVerified = true;
    }

    private void sendEmailToUser(User user, String link, String thymeleafName, String emailSubject) {
        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("firstName", user.getFirstName());
        templateModel.put("registerDate", new Date());
        templateModel.put("link", link);
        templateModel.put("logo", "static/logo.png");
        emailSender.sendEmail(user.getEmail(), emailSubject, thymeleafName, templateModel);
    }

    private String getTemporaryProfileImageUrl(String firstName, String lastName) {
        return ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path(FileConstant.DEFAULT_USER_IMAGE_PATH + firstName + "-" + lastName)
                .toUriString();
    }

    private String setProfileImageUrl(String firstName, String lastName) {
        return ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path(FileConstant.USER_IMAGE_PATH + firstName + "-" + lastName + "/" + firstName + "-" + lastName + "." + FileConstant.USER_EXTENSION)
                .toUriString();
    }

    private void saveProfileImage(User user, MultipartFile image) throws IOException {
        if (image != null) {
            // get location of user folder
            Path userFolder = Paths.get(FileConstant.USER_FOLDER + user.getFirstName() + "-" + user.getLastName()).toAbsolutePath().normalize();
            if (!Files.exists(userFolder)) {
                Files.createDirectories(userFolder);
            }
            Files.deleteIfExists(Paths.get(userFolder + user.getFirstName() + "-" + user.getLastName() + "." + FileConstant.USER_EXTENSION));
            Files.copy(image.getInputStream(), userFolder.resolve(user.getFirstName() + "-" + user.getLastName() + "." + FileConstant.USER_EXTENSION), StandardCopyOption.REPLACE_EXISTING);

            // set new image url
            user.setImage(setProfileImageUrl(user.getFirstName(), user.getLastName()));
            userRepository.save(user);
        }
    }

    private HttpHeaders getJwtHeader(CustomUserDetails user) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Jwt-Token", jwtProvider.generateToken(user));
        return httpHeaders;
    }

}
