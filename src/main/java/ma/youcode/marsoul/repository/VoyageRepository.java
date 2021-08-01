package ma.youcode.marsoul.repository;

import ma.youcode.marsoul.entity.Voyage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoyageRepository extends JpaRepository<Voyage, Long> {
    Optional<Voyage> findVoyageBySeatPosition(Integer seatPosition);
}
