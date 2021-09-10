package ma.youcode.marsoul.controller;

import ma.youcode.marsoul.dto.EquipmentDTO;
import ma.youcode.marsoul.entity.Equipment;
import ma.youcode.marsoul.service.EquipmentService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/marsoul/api/v1/equipments")
public class EquipmentController {

    @Autowired
    private EquipmentService equipmentService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<EquipmentDTO>> getAllEquipments() {
        List<Equipment> allEquipments = equipmentService.getAllEquipments();
            return ResponseEntity.ok().body(modelMapper.map(allEquipments, new TypeToken<List<EquipmentDTO>>(){}.getType()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EquipmentDTO> getEquipment(@PathVariable("id") Integer id) {
        Equipment equipment = equipmentService.getEquipmentById(id);
        return ResponseEntity.ok().body(modelMapper.map(equipment, EquipmentDTO.class));
    }

    @PostMapping
    public ResponseEntity<Equipment> addEquipment(@RequestBody EquipmentDTO equipmentDTO) {
        Equipment equipment = equipmentService.saveEquipment(modelMapper.map(equipmentDTO, Equipment.class));
        return ResponseEntity.status(HttpStatus.CREATED).body(equipment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Equipment> updateEquipment(@PathVariable("id") Integer id, @RequestBody EquipmentDTO equipmentDTO) {
        Equipment equipment = equipmentService.updateEquipment(id, modelMapper.map(equipmentDTO, Equipment.class));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(equipment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteEquipment(@PathVariable("id") Integer id) {
        equipmentService.deleteEquipmentById(id);
        return ResponseEntity.noContent().build();
    }

}
