package ma.youcode.marsoul.controller;

import ma.youcode.marsoul.entity.Bus;
import ma.youcode.marsoul.exception.VoyageNotExistException;
import ma.youcode.marsoul.service.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/marsoul/api/v1/buses")
public class BusController {

    @Autowired
    private BusService busService;

    @GetMapping
    public ResponseEntity<List<Bus>> getAllVoyages() {
        return ResponseEntity.status(HttpStatus.OK).body(busService.getAll());
    }

    @GetMapping("{busId}")
    public ResponseEntity<Bus> getVoyage(@PathVariable Integer busId) {
        return ResponseEntity.status(HttpStatus.OK).body(busService.getById(busId));
    }

    @PostMapping
    public ResponseEntity<Bus> addVoyage(@RequestBody @Valid Bus bus) {
        return ResponseEntity.status(HttpStatus.CREATED).body(busService.addOrUpdate(bus));
    }

    @PutMapping("{busId}")
    public ResponseEntity<Bus> updateVoyage(@PathVariable Integer busId, @RequestBody @Valid Bus bus) {
        Bus targetedBus = busService.getById(busId);
        if (targetedBus != null) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(busService.addOrUpdate(bus));
        } else {
            throw new VoyageNotExistException("Bus entity does not exist");
        }
    }

    @DeleteMapping("{busId}")
    public ResponseEntity<HttpStatus> deleteVoyage(@PathVariable Integer busId) {
        busService.deleteById(busId);
        return ResponseEntity.noContent().build();
    }
}
