package ma.youcode.marsoul.repository;

import ma.youcode.marsoul.entity.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Integer> {
    Optional<Equipment> findEquipmentByName(String name);
}
