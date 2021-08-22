package ma.youcode.marsoul.service;

import ma.youcode.marsoul.entity.VerificationToken;

import java.util.Optional;

public interface VerificationTokenService {
    void saveVerificationToken(VerificationToken verificationToken);
    Optional<VerificationToken> getToken(String token);
    void setConfirmedDate(String token);
}
