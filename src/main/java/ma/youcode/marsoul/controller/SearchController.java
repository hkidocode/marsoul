package ma.youcode.marsoul.controller;

import ma.youcode.marsoul.dto.BusDTO;
import ma.youcode.marsoul.entity.Bus;
import ma.youcode.marsoul.service.BusService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;

@RestController
@RequestMapping("/marsoul/api/v1/search")
public class SearchController {

    @Autowired
    private BusService busService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/buses")
    public ResponseEntity<Page<BusDTO>> getAllBuses(@RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                                    @RequestParam("startCity") String startCity,
                                                    @RequestParam("cityDestination") String cityDestination,
                                                    @RequestParam("voyageDate") String voyageDate
                                                    ) {
        Pageable pagingSort = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "startHour"));
        Page<Bus> allBuses = busService.searchBuses(pagingSort, startCity, cityDestination, Date.valueOf(voyageDate));
        return ResponseEntity.ok().body(modelMapper.map(allBuses, new TypeToken<Page<BusDTO>>(){}.getType()));
    }
}
