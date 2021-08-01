package ma.youcode.marsoul.controller;

import ma.youcode.marsoul.dto.SocietyDTO;
import ma.youcode.marsoul.entity.Society;
import ma.youcode.marsoul.service.impl.SocietyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/marsoul/api/v1/societies")
public class SocietyController {

    @Autowired
    private SocietyService societyService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<SocietyDTO> getAllSocieties() {
        List<Society> allSocieties = societyService.getAllSocieties();
        return ResponseEntity.ok().body(modelMapper.map(allSocieties, SocietyDTO.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SocietyDTO> getSociety(@PathVariable("id") Integer id) {
        Society society = societyService.getSocietyById(id);
        return ResponseEntity.ok().body(modelMapper.map(society, SocietyDTO.class));
    }

    @PostMapping
    public ResponseEntity<Society> addSociety(@RequestBody SocietyDTO societyDTO) {
        Society society = societyService.saveSociety(modelMapper.map(societyDTO, Society.class));
        return ResponseEntity.status(HttpStatus.CREATED).body(society);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Society> updateSociety(@PathVariable("id") Integer id, @RequestBody SocietyDTO societyDTO) {
        Society society = societyService.updateSociety(id, modelMapper.map(societyDTO, Society.class));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(society);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteSociety(@PathVariable("id") Integer id) {
        societyService.deleteSocietyById(id);
        return ResponseEntity.noContent().build();
    }

}
