package ma.youcode.marsoul.repository;

import ma.youcode.marsoul.entity.Bus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;

@Repository
public interface BusRepository extends JpaRepository<Bus, Integer> {
    Page<Bus> findAllByStartCityAndCityDestinationOrVoyageDate(
            Pageable pageable, String startCity, String cityDestination, Date voyageDate
    );
}
