package ma.youcode.marsoul.service;

import ma.youcode.marsoul.entity.Bus;
import ma.youcode.marsoul.exception.BusNotExistException;
import ma.youcode.marsoul.repository.BusRepository;
import ma.youcode.marsoul.service.impl.BusService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

public class BusServiceImpl implements BusService {

    @Autowired
    private BusRepository busRepository;

    @Override
    public Bus getBusById(Integer busId) {
        return  busRepository.findById(busId)
                    .orElseThrow(() -> new BusNotExistException("Bus does not exist"));
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
        Bus targetedBus = getBusById(busId);
        targetedBus.setStartCity(bus.getStartCity());
        targetedBus.setCityDestination(bus.getCityDestination());
        targetedBus.setStartAgency(bus.getStartAgency());
        targetedBus.setAgencyDestination(bus.getAgencyDestination());
        targetedBus.setVoyageDate(bus.getVoyageDate());
        targetedBus.setStartHour(bus.getStartHour());
        targetedBus.setEndHour(bus.getEndHour());
        targetedBus.setEmptyPlaces(bus.getEmptyPlaces());
        targetedBus.setUpdatedAt(new Date(System.currentTimeMillis()));
        return busRepository.save(targetedBus);
    }

    @Override
    public void deleteBusById(Integer busId) {
        if (busRepository.findById(busId).isPresent()) {
            busRepository.deleteById(busId);
        } else {
            throw new BusNotExistException("Bus does not exist");
        }
    }
}
