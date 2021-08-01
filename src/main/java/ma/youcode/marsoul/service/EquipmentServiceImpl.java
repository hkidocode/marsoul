package ma.youcode.marsoul.service;

import ma.youcode.marsoul.entity.Equipment;
import ma.youcode.marsoul.exception.EquipmentNotExistException;
import ma.youcode.marsoul.repository.EquipmentRepository;
import ma.youcode.marsoul.service.impl.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class EquipmentServiceImpl implements EquipmentService {

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Override
    public Equipment getEquipmentById(Integer equipmentId) {
        return equipmentRepository.findById(equipmentId)
                .orElseThrow(() -> new EquipmentNotExistException("Equipment entity does not exist"));
    }

    @Override
    public List<Equipment> getAllEquipments() {
        return equipmentRepository.findAll();
    }

    @Override
    public Equipment saveEquipment(Equipment equipment) {
        return equipmentRepository.save(equipment);
    }

    @Override
    public Equipment updateEquipment(Integer equipmentId, Equipment equipment) {
        return equipmentRepository.save(equipment);
    }

    @Override
    public void deleteEquipmentById(Integer equipmentId) {
        if (equipmentRepository.findById(equipmentId).isPresent()) {
            equipmentRepository.deleteById(equipmentId);
        } else {
            throw new EquipmentNotExistException("Equipment entity does not exist");
        }
    }
}
