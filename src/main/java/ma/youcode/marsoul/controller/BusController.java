package ma.youcode.marsoul.controller;

import ma.youcode.marsoul.dto.BusDTO;
import ma.youcode.marsoul.entity.Bus;
import ma.youcode.marsoul.service.BusService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/marsoul/api/v1/buses")
public class BusController {

    @Autowired
    private BusService busService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<BusDTO> getAllBuses() {
        List<Bus> allBuses = busService.getAllBuses();
        return ResponseEntity.ok().body(modelMapper.map(allBuses, BusDTO.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BusDTO> getBus(@PathVariable("id") Integer id) {
        Bus bus = busService.getBusById(id);
        return ResponseEntity.ok().body(modelMapper.map(bus, BusDTO.class));
    }

    @PostMapping
    public ResponseEntity<Bus> addBus(@RequestBody BusDTO busDTO) {
        Bus bus = busService.saveBus(modelMapper.map(busDTO, Bus.class));
        return ResponseEntity.status(HttpStatus.CREATED).body(bus);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Bus> updateBus(@PathVariable("id") Integer id, @RequestBody BusDTO busDTO) {
        Bus bus = busService.updateBus(id, modelMapper.map(busDTO, Bus.class));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(bus);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteBus(@PathVariable("id") Integer id) {
        busService.deleteBusById(id);
        return ResponseEntity.noContent().build();
    }

}
