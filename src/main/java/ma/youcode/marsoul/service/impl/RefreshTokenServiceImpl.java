package ma.youcode.marsoul.service.impl;

import ma.youcode.marsoul.entity.RefreshToken;
import ma.youcode.marsoul.entity.User;
import ma.youcode.marsoul.repository.RefreshTokenRepository;
import ma.youcode.marsoul.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Override
    public void saveRefreshToken(RefreshToken refreshToken) {
        refreshTokenRepository.save(refreshToken);
    }

    @Override
    public Optional<RefreshToken> getToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Override
    public void deleteToken(User user) {
        refreshTokenRepository.deleteToken(user);
    }

}
