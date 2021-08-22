package ma.youcode.marsoul.service;

import ma.youcode.marsoul.entity.RefreshToken;

import java.util.Optional;

public interface RefreshTokenService {
    void saveRefreshToken(RefreshToken refreshToken);
    Optional<RefreshToken> getToken(String token);
    void deleteToken(String token);
}
