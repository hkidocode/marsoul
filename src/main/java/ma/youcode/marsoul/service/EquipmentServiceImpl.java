package ma.youcode.marsoul.service;

import ma.youcode.marsoul.entity.Equipment;
import ma.youcode.marsoul.exception.EquipmentExistException;
import ma.youcode.marsoul.exception.EquipmentNotExistException;
import ma.youcode.marsoul.repository.EquipmentRepository;
import ma.youcode.marsoul.service.impl.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Transactional
public class EquipmentServiceImpl implements EquipmentService {

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Override
    public Equipment getEquipmentById(Integer equipmentId) {
        return equipmentRepository.findById(equipmentId)
                .orElseThrow(() -> new EquipmentNotExistException("Equipment does not exist"));
    }

    @Override
    public List<Equipment> getAllEquipments() {
        return equipmentRepository.findAll();
    }

    @Override
    public Equipment saveEquipment(Equipment equipment) {
        Optional<Equipment> equipmentByName = equipmentRepository.findEquipmentByName(equipment.getName());
        if (equipmentByName.isPresent()) {
            throw new EquipmentExistException("Equipment already exist");
        }
        return equipmentRepository.save(equipment);
    }

    @Override
    public Equipment updateEquipment(Integer equipmentId, Equipment equipment) {
        Equipment targetedEquipment = getEquipmentById(equipmentId);
        targetedEquipment.setName(equipment.getName());
        targetedEquipment.setUpdatedAt(new Date(System.currentTimeMillis()));
        return equipmentRepository.save(equipment);
    }

    @Override
    public void deleteEquipmentById(Integer equipmentId) {
        if (equipmentRepository.findById(equipmentId).isPresent()) {
            equipmentRepository.deleteById(equipmentId);
        } else {
            throw new EquipmentNotExistException("Equipment does not exist");
        }
    }
}
