package ma.youcode.marsoul.controller;

import ma.youcode.marsoul.dto.EquipmentDTO;
import ma.youcode.marsoul.entity.Equipment;
import ma.youcode.marsoul.mapper.EquipmentMapper;
import ma.youcode.marsoul.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/marsoul/api/v1/equipments")
public class EquipmentController {

    @Autowired
    private EquipmentService equipmentService;

    @Autowired
    private EquipmentMapper equipmentMapper;

    @GetMapping
    public ResponseEntity<List<EquipmentDTO>> getAllEquipments() {
        return ResponseEntity.status(HttpStatus.OK).body(equipmentMapper.entitiesToDTOs(equipmentService.getAll()));
    }

    @GetMapping("{equipmentId}")
    public ResponseEntity<EquipmentDTO> getEquipment(@PathVariable Integer equipmentId) {
        return ResponseEntity.status(HttpStatus.OK).body(equipmentMapper.entityToDTO(equipmentService.getById(equipmentId)));
    }

    @PostMapping
    public ResponseEntity<Equipment> addEquipment(@RequestBody @Valid EquipmentDTO equipmentDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(equipmentService.addOrUpdate(equipmentMapper.dtoToEntity(equipmentDTO)));
    }

    @PutMapping("{equipmentId}")
    public ResponseEntity<Equipment> updateEquipment(@PathVariable Integer equipmentId, @RequestBody @Valid Equipment equipment) {
        Equipment targetedEquipment = equipmentService.getById(equipmentId);
        targetedEquipment.setName(equipment.getName());

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(equipmentService.addOrUpdate(targetedEquipment));

    }

    @DeleteMapping("{equipmentId}")
    public ResponseEntity<HttpStatus> deleteEquipment(@PathVariable Integer equipmentId) {
        equipmentService.deleteById(equipmentId);
        return ResponseEntity.noContent().build();
    }
}
