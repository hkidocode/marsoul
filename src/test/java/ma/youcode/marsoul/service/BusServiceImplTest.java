package ma.youcode.marsoul.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BusServiceImplTest {

//    @Mock
//    private BusRepository busRepository;
//
//    @InjectMocks
//    private BusServiceImpl busService;
//
//    Bus bus1 = new Bus(1, "Agadir",
//            "Casablanca",
//            "CTM Agadir Agency",
//            "CTM Casablanca Agency",
//            new Date(System.currentTimeMillis()),
//            Time.valueOf("10:00:00"),
//            Time.valueOf("16:30:00"),
//            9, 200);
//
//    Bus bus2 = new Bus(2, "Marrakech",
//            "Safi",
//            "CTM Marrakech Agency",
//            "CTM Safi Agency",
//            new Date(System.currentTimeMillis()),
//            Time.valueOf("18:00:00"),
//            Time.valueOf("20:30:00"),
//            20, 300);
//
//    List<Bus> buses = new ArrayList<>();
//
//    @Test
//    void shouldReturnSavedBusStartCity() {
//        when(busRepository.save(any(Bus.class))).thenReturn(bus1);
//        Bus savedBus = busService.saveBus(bus1);
//        assertThat(savedBus.getStartCity()).isEqualTo("Agadir");
//    }
//
//    @Test
//    void shouldReturnBusOfIdOne() {
//        when(busRepository.findById(1)).thenReturn(Optional.of(bus1));
//        assertThat(busService.getBusById(bus1.getId())).isEqualTo(bus1);
//    }

//    @Test
//    void shouldReturnListOfBuses() {
//        buses.add(bus1);
//        buses.add(bus2);
//        busRepository.save(bus1);
//        when(busRepository.findAll()).thenReturn(buses);
//        List<Bus> busList = busService.getAllBuses();
//        assertEquals(buses, busList);
//        verify(busRepository, times(1)).save(bus1);
//        verify(busRepository, times(1)).findAll();
//    }

}