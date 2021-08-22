package ma.youcode.marsoul.repository;

import ma.youcode.marsoul.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    Optional<VerificationToken> findByToken(String token);

    @Transactional
    @Modifying
    @Query("UPDATE VerificationToken c " + "SET c.confirmDate = ?1 " + "WHERE c.token = ?2")
    void updateConfirmDate(LocalDateTime confirmDate, String token);
}
