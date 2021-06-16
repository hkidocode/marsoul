package ma.youcode.marsoul.controller;

import ma.youcode.marsoul.entity.Equipment;
import ma.youcode.marsoul.exception.VoyageNotExistException;
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

    @GetMapping
    public ResponseEntity<List<Equipment>> getAllEquipments() {
        return ResponseEntity.status(HttpStatus.OK).body(equipmentService.getAll());
    }

    @GetMapping("{equipmentId}")
    public ResponseEntity<Equipment> getEquipment(@PathVariable Integer equipmentId) {
        return ResponseEntity.status(HttpStatus.OK).body(equipmentService.getById(equipmentId));
    }

    @PostMapping
    public ResponseEntity<Equipment> addEquipment(@RequestBody @Valid Equipment equipment) {
        return ResponseEntity.status(HttpStatus.CREATED).body(equipmentService.addOrUpdate(equipment));
    }

    @PutMapping("{equipmentId}")
    public ResponseEntity<Equipment> updateEquipment(@PathVariable Integer equipmentId, @RequestBody @Valid Equipment equipment) {
        Equipment targetedEquipment = equipmentService.getById(equipmentId);
        if (targetedEquipment != null) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(equipmentService.addOrUpdate(equipment));
        } else {
            throw new VoyageNotExistException("Equipment entity does not exist");
        }
    }

    @DeleteMapping("{equipmentId}")
    public ResponseEntity<Object> deleteEquipment(@PathVariable Integer equipmentId) {
        equipmentService.deleteById(equipmentId);
        return ResponseEntity.noContent().build();
    }
}
