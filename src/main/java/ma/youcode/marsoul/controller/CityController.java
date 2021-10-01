package ma.youcode.marsoul.controller;

import ma.youcode.marsoul.entity.City;
import ma.youcode.marsoul.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/marsoul/api/v1/cities")
public class CityController {

    @Autowired
    private CityService cityService;

    @GetMapping
    public ResponseEntity<List<City>> getAllBuses() {
        return ResponseEntity.ok().body(cityService.getCities());
    }
}
