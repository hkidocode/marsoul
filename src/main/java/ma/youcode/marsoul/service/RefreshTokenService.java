package ma.youcode.marsoul.service;

import ma.youcode.marsoul.entity.RefreshToken;
import ma.youcode.marsoul.entity.User;

import java.util.Optional;

public interface RefreshTokenService {
    void saveRefreshToken(RefreshToken refreshToken);
    Optional<RefreshToken> getToken(String token);
    void deleteToken(User user);
}
