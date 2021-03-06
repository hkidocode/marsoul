package ma.youcode.marsoul.controller;

import ma.youcode.marsoul.dto.VoyageDTO;
import ma.youcode.marsoul.entity.Voyage;
import ma.youcode.marsoul.service.VoyageService;
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
@RequestMapping("/marsoul/api/v1/voyages")
public class VoyageController {

    @Autowired
    private VoyageService voyageService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<Page<VoyageDTO>> getAllVoyages(@RequestParam(name = "page", required = false, defaultValue = "0") int page) {
        Pageable pagingSort = PageRequest.of(page, 7, Sort.by(Sort.Direction.DESC, "id"));
        Page<Voyage> allVoyages = voyageService.getAllVoyages(pagingSort);
        return ResponseEntity.ok().body(modelMapper.map(allVoyages, new TypeToken<Page<VoyageDTO>>() {
        }.getType()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VoyageDTO> getVoyage(@PathVariable("id") Long id) {
        Voyage voyage = voyageService.getVoyageById(id);
        return ResponseEntity.ok().body(modelMapper.map(voyage, VoyageDTO.class));
    }

    @PostMapping
    public ResponseEntity<Voyage> addVoyage(@RequestBody VoyageDTO voyageDTO) {
        Voyage voyage = voyageService.saveVoyage(modelMapper.map(voyageDTO, Voyage.class));
        return ResponseEntity.status(HttpStatus.CREATED).body(voyage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Voyage> updateVoyage(@PathVariable("id") Long id, @RequestBody VoyageDTO voyageDTO) {
        Voyage voyage = voyageService.updateVoyage(id, modelMapper.map(voyageDTO, Voyage.class));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(voyage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteVoyage(@PathVariable("id") Long id) {
        voyageService.deleteVoyageById(id);
        return ResponseEntity.noContent().build();
    }

}
