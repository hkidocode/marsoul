package ma.youcode.marsoul.service.impl;

import ma.youcode.marsoul.entity.City;
import ma.youcode.marsoul.repository.CityRepository;
import ma.youcode.marsoul.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityRepository cityRepository;

    @Override
    public List<City> getCities() {
        return cityRepository.findAll();
    }
}
