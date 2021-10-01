package ma.youcode.marsoul.service;

import ma.youcode.marsoul.entity.Equipment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EquipmentService {
    Equipment getEquipmentById(Integer equipmentId);
    Page<Equipment> getAllEquipments(Pageable pageable);
    Equipment saveEquipment(Equipment equipment);
    Equipment updateEquipment(Integer equipmentId, Equipment equipment);
    void deleteEquipmentById(Integer equipmentId);
}
