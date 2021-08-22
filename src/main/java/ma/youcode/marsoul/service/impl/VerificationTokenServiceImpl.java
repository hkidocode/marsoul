package ma.youcode.marsoul.service.impl;

import ma.youcode.marsoul.entity.VerificationToken;
import ma.youcode.marsoul.repository.VerificationTokenRepository;
import ma.youcode.marsoul.service.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class VerificationTokenServiceImpl implements VerificationTokenService {

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Override
    public void saveVerificationToken(VerificationToken verificationToken) {
        verificationTokenRepository.save(verificationToken);
    }

    @Override
    public Optional<VerificationToken> getToken(String token) {
        return verificationTokenRepository.findByToken(token);
    }

    @Override
    public void setConfirmedDate(String token) {
        verificationTokenRepository.updateConfirmDate(LocalDateTime.now(),token);
    }

}
