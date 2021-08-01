package ma.youcode.marsoul.controller;

import ma.youcode.marsoul.dto.BusDTO;
import ma.youcode.marsoul.entity.Bus;
import ma.youcode.marsoul.mapper.BusMapper;
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

    @Autowired
    private BusMapper busMapper;

    @GetMapping
    public ResponseEntity<List<BusDTO>> getAllBuses() {
        return ResponseEntity.status(HttpStatus.OK).body(busMapper.entitiesToDTOs(busService.getAll()));
    }

    @GetMapping("{busId}")
    public ResponseEntity<BusDTO> getBus(@PathVariable Integer busId) {
        return ResponseEntity.status(HttpStatus.OK).body(busMapper.entityToDTO(busService.getById(busId)));
    }

    @PostMapping
    public ResponseEntity<Bus> addBus(@RequestBody @Valid BusDTO busDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(busService.addOrUpdate(busMapper.dtoToEntity(busDTO)));
    }

    @PutMapping("{busId}")
    public ResponseEntity<Bus> updateBus(@PathVariable Integer busId, @RequestBody @Valid Bus bus) {
        Bus targetedBus = busService.getById(busId);
        targetedBus.setVoyageDate(bus.getVoyageDate());
        targetedBus.setStartHour(bus.getStartHour());
        targetedBus.setEndHour(bus.getEndHour());
        targetedBus.setStartCity(bus.getStartCity());
        targetedBus.setCityDestination(bus.getCityDestination());
        targetedBus.setStartAgency(bus.getStartAgency());
        targetedBus.setAgencyDestination(bus.getAgencyDestination());
        targetedBus.setPeople(bus.getPeople());
        targetedBus.setSociety(bus.getSociety());
        targetedBus.setEquipments(bus.getEquipments());

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(busService.addOrUpdate(targetedBus));

    }

    @DeleteMapping("{busId}")
    public ResponseEntity<HttpStatus> deleteBus(@PathVariable Integer busId) {
        busService.deleteById(busId);
        return ResponseEntity.noContent().build();
    }
}
