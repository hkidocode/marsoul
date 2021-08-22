package ma.youcode.marsoul.service;

import ma.youcode.marsoul.entity.PasswordToken;

import java.util.Optional;

public interface PasswordTokenService {
    void savePasswordToken(PasswordToken passwordToken);
    Optional<PasswordToken> getToken(String token);
    void setConfirmedDate(String token);
}
