package ma.youcode.marsoul.service;

import ma.youcode.marsoul.entity.Bus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Date;

public interface BusService {
    Bus getBusById(Integer busId);
    Page<Bus> getAllBuses(Pageable pageable);
    Bus saveBus(Bus bus);
    Bus updateBus(Integer busId, Bus bus);
    void deleteBusById(Integer busId);
    Page<Bus> searchBuses(Pageable pageable, String startCity, String cityDestination, Date voyageDate);
}
