package ma.youcode.marsoul.service;

import ma.youcode.marsoul.dto.request.LoginRequest;
import ma.youcode.marsoul.dto.request.ResetPasswordRequest;
import ma.youcode.marsoul.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface UserService {
    User getUserById(Long userId);
    List<User> getAllUsers();
    User saveUser(User user, Collection<String> roleNames);
    User updateUser(Long userId, User user);
    void deleteUserById(Long userId);
    User updateProfileImage(Long id, MultipartFile image) throws IOException;
    void verifyAccount(String token);
    String verifyRefreshToken(String token);
    String loginAccount(LoginRequest loginRequest) throws ExecutionException;
    void logoutAccount(String token);
    void sendResetPassword(String email);
    void updatePassword(ResetPasswordRequest resetPasswordRequest, String token);

}
