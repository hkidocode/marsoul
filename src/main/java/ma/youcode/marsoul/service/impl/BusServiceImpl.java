package ma.youcode.marsoul.service.impl;

import ma.youcode.marsoul.entity.Bus;
import ma.youcode.marsoul.exception.EntityExistException;
import ma.youcode.marsoul.exception.EntityNotExistException;
import ma.youcode.marsoul.repository.BusRepository;
import ma.youcode.marsoul.service.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BusServiceImpl implements BusService {

    @Autowired
    private BusRepository busRepository;

    @Override
    public Bus getBusById(Integer busId) {
        return  busRepository.findById(busId)
                    .orElseThrow(() -> new EntityNotExistException("Bus does not exist"));
    }

    @Override
    public List<Bus> getAllBuses() {
        return busRepository.findAll();
    }

    @Override
    public Bus saveBus(Bus bus) {
        Optional<Bus> busById = busRepository.findById(bus.getId());
        if (busById.isPresent()) {
            throw new EntityExistException("Bus already exist");
        }
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
        return busRepository.save(targetedBus);
    }

    @Override
    public void deleteBusById(Integer busId) {
        if (busRepository.findById(busId).isPresent()) {
            busRepository.deleteById(busId);
        } else {
            throw new EntityNotExistException("Bus does not exist");
        }
    }
}
