package ma.youcode.marsoul.controller;

import ma.youcode.marsoul.entity.Society;
import ma.youcode.marsoul.exception.VoyageNotExistException;
import ma.youcode.marsoul.service.SocietyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/marsoul/api/v1/societies")
public class SocietyController {

    @Autowired
    private SocietyService societyService;

    @GetMapping
    public ResponseEntity<List<Society>> getAllSocieties() {
        return ResponseEntity.status(HttpStatus.OK).body(societyService.getAll());
    }

    @GetMapping("{societyId}")
    public ResponseEntity<Society> getSociety(@PathVariable Integer societyId) {
        return ResponseEntity.status(HttpStatus.OK).body(societyService.getById(societyId));
    }

    @PostMapping
    public ResponseEntity<Society> addSociety(@RequestBody @Valid Society society) {
        return ResponseEntity.status(HttpStatus.CREATED).body(societyService.addOrUpdate(society));
    }

    @PutMapping("{societyId}")
    public ResponseEntity<Society> updateSociety(@PathVariable Integer societyId, @RequestBody @Valid Society society) {
        Society targetedSociety = societyService.getById(societyId);
        if (targetedSociety != null) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(societyService.addOrUpdate(society));
        } else {
            throw new VoyageNotExistException("Person entity does not exist");
        }
    }

    @DeleteMapping("{societyId}")
    public ResponseEntity<HttpStatus> deleteSociety(@PathVariable Integer societyId) {
        societyService.deleteById(societyId);
        return ResponseEntity.noContent().build();
    }
}
