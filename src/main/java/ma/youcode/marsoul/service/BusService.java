package ma.youcode.marsoul.service;

import ma.youcode.marsoul.entity.Bus;
import ma.youcode.marsoul.repository.BusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusService {

    @Autowired
    private BusRepository busRepository;

    public Bus getById(Integer busId) {
        if (busRepository.findById(busId).isPresent()) {
            return busRepository.findById(busId).get();
        } else {
            throw new BusNotExistException("Bus entity does not exist");
        }
    }

    public List<Bus> getAll() {
        return busRepository.findAll();
    }

    public Bus addOrUpdate(Bus bus) {
        return busRepository.save(bus);
    }

    public void deleteById(Integer busId) {
        if (busRepository.findById(busId).isPresent()) {
            busRepository.deleteById(busId);
        } else {
            throw new BusNotExistException("Bus entity does not exist");
        }
    }
}
