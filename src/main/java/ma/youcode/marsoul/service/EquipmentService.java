package ma.youcode.marsoul.service;

import ma.youcode.marsoul.entity.Equipment;
import org.springframework.stereotype.Service;

import java.util.List;

public interface EquipmentService {
    Equipment getEquipmentById(Integer equipmentId);
    List<Equipment> getAllEquipments();
    Equipment saveEquipment(Equipment equipment);
    Equipment updateEquipment(Integer equipmentId, Equipment equipment);
    void deleteEquipmentById(Integer equipmentId);
}
