package ma.youcode.marsoul.controller;

import ma.youcode.marsoul.dto.SocietyDTO;
import ma.youcode.marsoul.entity.Society;
import ma.youcode.marsoul.mapper.SocietyMapper;
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

    @Autowired
    private SocietyMapper societyMapper;

    @GetMapping
    public ResponseEntity<List<SocietyDTO>> getAllSocieties() {
        return ResponseEntity.status(HttpStatus.OK).body(societyMapper.entitiesToDTOs(societyService.getAll()));
    }

    @GetMapping("{societyId}")
    public ResponseEntity<SocietyDTO> getSociety(@PathVariable Integer societyId) {
        return ResponseEntity.status(HttpStatus.OK).body(societyMapper.entityToDTO(societyService.getById(societyId)));
    }

    @PostMapping
    public ResponseEntity<Society> addSociety(@RequestBody @Valid SocietyDTO societyDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(societyService.addOrUpdate(societyMapper.dtoToEntity(societyDTO)));
    }

    @PutMapping("{societyId}")
    public ResponseEntity<Society> updateSociety(@PathVariable Integer societyId, @RequestBody @Valid Society society) {
        Society targetedSociety = societyService.getById(societyId);
        targetedSociety.setName(society.getName());
        targetedSociety.setBuses(society.getBuses());
        targetedSociety.setBusCount(society.getBuses().size());

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(societyService.addOrUpdate(society));

    }

    @DeleteMapping("{societyId}")
    public ResponseEntity<HttpStatus> deleteSociety(@PathVariable Integer societyId) {
        societyService.deleteById(societyId);
        return ResponseEntity.noContent().build();
    }
}
