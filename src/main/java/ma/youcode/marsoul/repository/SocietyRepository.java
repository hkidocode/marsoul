package ma.youcode.marsoul.repository;

import ma.youcode.marsoul.entity.Society;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SocietyRepository extends JpaRepository<Society, Integer> {
    Optional<Society> findSocietyByName(String name);
}
