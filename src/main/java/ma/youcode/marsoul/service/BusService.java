package ma.youcode.marsoul.service;

import ma.youcode.marsoul.entity.Bus;
import org.springframework.stereotype.Service;

import java.util.List;

public interface BusService {
    Bus getBusById(Integer busId);
    List<Bus> getAllBuses();
    Bus saveBus(Bus bus);
    Bus updateBus(Integer busId, Bus bus);
    void deleteBusById(Integer busId);
}
