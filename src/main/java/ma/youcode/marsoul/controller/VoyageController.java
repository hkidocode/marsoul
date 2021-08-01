package ma.youcode.marsoul.controller;

import ma.youcode.marsoul.dto.VoyageDTO;
import ma.youcode.marsoul.entity.Voyage;
import ma.youcode.marsoul.mapper.VoyageMapper;
import ma.youcode.marsoul.service.VoyageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/marsoul/api/v1/voyages")
public class VoyageController {

    @Autowired
    private VoyageService voyageService;

    @Autowired
    private VoyageMapper voyageMapper;

    @GetMapping
    public ResponseEntity<List<VoyageDTO>> getAllVoyages() {
        return ResponseEntity.status(HttpStatus.OK).body(voyageMapper.entitiesToDTOs(voyageService.getAll()));
    }

    @GetMapping("{voyageId}")
    public ResponseEntity<VoyageDTO> getVoyage(@PathVariable Long voyageId) {
        return ResponseEntity.status(HttpStatus.OK).body(voyageMapper.entityToDTO(voyageService.getById(voyageId)));
    }

    @PostMapping
    public ResponseEntity<Voyage> addVoyage(@RequestBody @Valid VoyageDTO voyageDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(voyageService.addOrUpdate(voyageMapper.dtoToEntity(voyageDTO)));
    }

    @PutMapping("{voyageId}")
    public ResponseEntity<Voyage> updateVoyage(@PathVariable Long voyageId, @RequestBody @Valid Voyage voyage) {
        Voyage targetedVoyage = voyageService.getById(voyageId);
        targetedVoyage.setSeatPosition(voyage.getSeatPosition());
        targetedVoyage.setStatus(voyage.getStatus());

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(voyageService.addOrUpdate(voyage));
    
    }

    @DeleteMapping("{voyageId}")
    public ResponseEntity<HttpStatus> deleteVoyage(@PathVariable Long voyageId) {
        voyageService.deleteById(voyageId);
        return ResponseEntity.noContent().build();
    }
}
