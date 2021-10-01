package ma.youcode.marsoul.controller;

import ma.youcode.marsoul.dto.SocietyDTO;
import ma.youcode.marsoul.entity.Society;
import ma.youcode.marsoul.service.SocietyService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/marsoul/api/v1/societies")
public class SocietyController {

    @Autowired
    private SocietyService societyService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<Page<SocietyDTO>> getAllSocieties(@RequestParam(name = "page", required = false, defaultValue = "0") int page) {
        Pageable pagingSort = PageRequest.of(page, 9, Sort.by(Sort.Direction.ASC, "name"));
        Page<Society> allSocieties = societyService.getAllSocieties(pagingSort);
        return ResponseEntity.ok().body(modelMapper.map(allSocieties, new TypeToken<Page<SocietyDTO>>() {
        }.getType()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SocietyDTO> getSociety(@PathVariable("id") Long id) {
        Society society = societyService.getSocietyById(id);
        return ResponseEntity.ok().body(modelMapper.map(society, SocietyDTO.class));
    }

    @PostMapping
    public ResponseEntity<Society> addSociety(@RequestBody SocietyDTO societyDTO) {
        Society society = societyService.saveSociety(modelMapper.map(societyDTO, Society.class));
        return ResponseEntity.status(HttpStatus.CREATED).body(society);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Society> updateSociety(@PathVariable("id") Long id, @RequestBody SocietyDTO societyDTO) {
        Society society = societyService.updateSociety(id, modelMapper.map(societyDTO, Society.class));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(society);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteSociety(@PathVariable("id") Long id) {
        societyService.deleteSocietyById(id);
        return ResponseEntity.noContent().build();
    }

}
