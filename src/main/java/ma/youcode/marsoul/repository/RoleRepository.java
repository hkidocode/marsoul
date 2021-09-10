package ma.youcode.marsoul.repository;

import ma.youcode.marsoul.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findRoleByName(String name);
    Role findByName(String name);
}
