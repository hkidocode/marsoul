package ma.youcode.marsoul.repository;

import ma.youcode.marsoul.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findPersonByEmail(String email);
}
