package ma.youcode.marsoul.controller;

import ma.youcode.marsoul.config.security.CustomUserDetails;
import ma.youcode.marsoul.dto.UserDTO;
import ma.youcode.marsoul.dto.request.EmailRequest;
import ma.youcode.marsoul.dto.request.LoginRequest;
import ma.youcode.marsoul.dto.request.ResetPasswordRequest;
import ma.youcode.marsoul.entity.User;
import ma.youcode.marsoul.message.AuthenticationResponse;
import ma.youcode.marsoul.message.MessageResponse;
import ma.youcode.marsoul.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/marsoul/api/v1/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/register")
    public ResponseEntity<MessageResponse> signupUser(@RequestBody UserDTO userDTO) {
        userService.saveUser(modelMapper.map(userDTO, User.class), userDTO.getRoles());
        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("You have signup successfully! Please check your email to verify your account"));
    }

    @GetMapping("/signup/confirm/{token}")
    public ResponseEntity<MessageResponse> verifyAccount(@PathVariable("token") String token) {
        userService.verifyAccount(token);
        return ResponseEntity.ok().body(new MessageResponse("Your account has activated successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest loginRequest) throws ExecutionException {
        HttpHeaders accessToken = userService.loginAccount(loginRequest);
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok().body(new AuthenticationResponse(accessToken, userDetails));
    }

    // TODO: Check Here -----
//    @PostMapping("/refresh/token")
//    public ResponseEntity<MessageResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
//        String token = userService.verifyRefreshToken(refreshTokenRequest.getRefreshToken());
//        return ResponseEntity.ok().body(new MessageResponse("Refresh token verified successfully with new token " + token));
//    }

    @PostMapping("/logout")
    public ResponseEntity<MessageResponse> logout(@RequestHeader("Authorization") String authorization) {
        userService.logoutAccount(authorization);
        return ResponseEntity.ok().body(new MessageResponse("Logout successfully"));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<MessageResponse> sendRestPasswordMail(@RequestBody EmailRequest emailRequest) {
        userService.sendResetPassword(emailRequest.getEmail());
        return ResponseEntity.ok().body(new MessageResponse("Please check your email to reset your password"));
    }

    @PostMapping("/reset-password/{token}")
    public ResponseEntity<MessageResponse> verifyResetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest, @PathVariable("token") String token) {
        userService.updatePassword(resetPasswordRequest, token);
        return ResponseEntity.ok().body(new MessageResponse("Reset password successfully"));
    }

}
