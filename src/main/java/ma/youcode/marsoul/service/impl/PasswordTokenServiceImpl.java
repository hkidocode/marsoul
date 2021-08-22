package ma.youcode.marsoul.service.impl;

import ma.youcode.marsoul.entity.PasswordToken;
import ma.youcode.marsoul.repository.PasswordTokenRepository;
import ma.youcode.marsoul.service.PasswordTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PasswordTokenServiceImpl implements PasswordTokenService {

    @Autowired
    private PasswordTokenRepository passwordTokenRepository;

    @Override
    public void savePasswordToken(PasswordToken passwordToken) {
        passwordTokenRepository.save(passwordToken);
    }

    @Override
    public Optional<PasswordToken> getToken(String token) {
        return passwordTokenRepository.findByToken(token);
    }

    @Override
    public void setConfirmedDate(String token) {
        passwordTokenRepository.updateConfirmDate(LocalDateTime.now(), token);
    }
}
