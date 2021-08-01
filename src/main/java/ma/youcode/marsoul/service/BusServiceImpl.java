package ma.youcode.marsoul.service;

import ma.youcode.marsoul.entity.Bus;
import ma.youcode.marsoul.exception.BusNotExistException;
import ma.youcode.marsoul.repository.BusRepository;
import ma.youcode.marsoul.service.impl.BusService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BusServiceImpl implements BusService {

    @Autowired
    private BusRepository busRepository;

    @Override
    public Bus getBusById(Integer busId) {
        return  busRepository.findById(busId)
                    .orElseThrow(() -> new BusNotExistException("Bus entity does not exist"));
    }

    @Override
    public List<Bus> getAllBuses() {
        return busRepository.findAll();
    }

    @Override
    public Bus saveBus(Bus bus) {
        return busRepository.save(bus);
    }

    @Override
    public Bus updateBus(Integer busId, Bus bus) {
        return busRepository.save(bus);
    }

    @Override
    public void deleteBusById(Integer busId) {
        if (busRepository.findById(busId).isPresent()) {
            busRepository.deleteById(busId);
        } else {
            throw new BusNotExistException("Bus entity does not exist");
        }
    }
}
