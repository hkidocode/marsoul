package ma.youcode.marsoul.service.impl;

import ma.youcode.marsoul.entity.Equipment;
import ma.youcode.marsoul.exception.EntityExistException;
import ma.youcode.marsoul.exception.EntityNotExistException;
import ma.youcode.marsoul.repository.EquipmentRepository;
import ma.youcode.marsoul.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
@Transactional
public class EquipmentServiceImpl implements EquipmentService {

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Override
    public Equipment getEquipmentById(Integer equipmentId) {
        return equipmentRepository.findById(equipmentId)
                .orElseThrow(() -> new EntityNotExistException("Equipment does not exist"));
    }

    @Override
    public Page<Equipment> getAllEquipments(Pageable pageable) {
        return equipmentRepository.findAll(pageable);
    }

    @Override
    public Equipment saveEquipment(Equipment equipment) {
        Optional<Equipment> equipmentByName = equipmentRepository.findByName(equipment.getName());
        if (equipmentByName.isPresent()) {
            throw new EntityExistException("Equipment already exist");
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
            throw new EntityNotExistException("Equipment does not exist");
        }
    }
}
