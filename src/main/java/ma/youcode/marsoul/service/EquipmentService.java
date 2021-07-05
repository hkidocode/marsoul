package ma.youcode.marsoul.service;

import ma.youcode.marsoul.entity.Equipment;
import ma.youcode.marsoul.exception.EquipmentNotExistException;
import ma.youcode.marsoul.repository.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentService {

    @Autowired
    private EquipmentRepository equipmentRepository;

    public Equipment getById(Integer equipmentId) {
        return equipmentRepository.findById(equipmentId)
                .orElseThrow(() -> new EquipmentNotExistException("Equipment entity does not exist"));
    }

    public List<Equipment> getAll() {
        return equipmentRepository.findAll();
    }

    public Equipment addOrUpdate(Equipment equipment) {
        return equipmentRepository.save(equipment);
    }

    public void deleteById(Integer equipmentId) {
        if (equipmentRepository.findById(equipmentId).isPresent()) {
            equipmentRepository.deleteById(equipmentId);
        } else {
            throw new EquipmentNotExistException("Equipment entity does not exist");
        }
    }
}
