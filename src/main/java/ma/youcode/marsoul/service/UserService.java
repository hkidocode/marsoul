package ma.youcode.marsoul.service;

import ma.youcode.marsoul.dto.request.LoginRequest;
import ma.youcode.marsoul.entity.User;
import ma.youcode.marsoul.dto.request.RefreshTokenRequest;
import ma.youcode.marsoul.dto.request.ResetPasswordRequest;

import java.util.Collection;
import java.util.List;

public interface UserService {
    User getUserById(Long userId);
    List<User> getAllUsers();
    User saveUser(User user, Collection<String> roleNames);
    User updateUser(Long userId, User user);
    void deleteUserById(Long userId);
    void verifyAccount(String token);
    String verifyRefreshToken(String token);
    String loginAccount(LoginRequest loginRequest);
    void logoutAccount(RefreshTokenRequest refreshTokenRequest);
    void resetPassword(String email);
    void verifyResetPassword(String token);
    void updatePassword(ResetPasswordRequest resetPasswordRequest);
}
