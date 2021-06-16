package ma.youcode.marsoul.controller;

import ma.youcode.marsoul.entity.Voyage;
import ma.youcode.marsoul.exception.VoyageNotExistException;
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

    @GetMapping
    public ResponseEntity<List<Voyage>> getAllVoyages() {
        return ResponseEntity.status(HttpStatus.OK).body(voyageService.getAll());
    }

    @GetMapping("{voyageId}")
    public ResponseEntity<Voyage> getVoyage(@PathVariable Long voyageId) {
        return ResponseEntity.status(HttpStatus.OK).body(voyageService.getById(voyageId));
    }

    @PostMapping
    public ResponseEntity<Voyage> addVoyage(@RequestBody @Valid Voyage voyage) {
        return ResponseEntity.status(HttpStatus.CREATED).body(voyageService.addOrUpdate(voyage));
    }

    @PutMapping("{voyageId}")
    public ResponseEntity<Voyage> updateVoyage(@PathVariable Long voyageId, @RequestBody @Valid Voyage voyage) {
        Voyage targetedVoyage = voyageService.getById(voyageId);
        if (targetedVoyage != null) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(voyageService.addOrUpdate(voyage));
        } else {
            throw new VoyageNotExistException("Person entity does not exist");
        }
    }

    @DeleteMapping("{voyageId}")
    public ResponseEntity<Object> deleteVoyage(@PathVariable Long voyageId) {
        voyageService.deleteById(voyageId);
        return ResponseEntity.noContent().build();
    }
}
