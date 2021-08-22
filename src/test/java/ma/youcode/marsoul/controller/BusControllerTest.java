package ma.youcode.marsoul.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ma.youcode.marsoul.dto.BusDTO;
import ma.youcode.marsoul.entity.Bus;
import ma.youcode.marsoul.service.impl.BusServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.sql.Time;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BusControllerTest {

    @MockBean
    private BusServiceImpl busService;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ModelMapper modelMapper;

    Bus bus = new Bus(1, "Agadir",
            "Casablanca",
            "CTM Agadir Agency",
            "CTM Casablanca Agency",
            new Date(System.currentTimeMillis()),
            Time.valueOf("10:00:00"),
            Time.valueOf("16:30:00"),
            9);

    BusDTO busDTO = new BusDTO("Agadir",
            "Casablanca",
            "CTM Agadir Agency",
            "CTM Casablanca Agency",
            new Date(System.currentTimeMillis()),
            Time.valueOf("10:00:00"),
            Time.valueOf("16:30:00"));


    @Test
    void shouldGetMappingOfAllBuses() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/marsoul/api/v1/buses").
                contentType(MediaType.APPLICATION_JSON).
                content(asJsonString(busDTO))).
                andDo(MockMvcResultHandlers.print());
        verify(busService).getAllBuses();
        verify(busService,times(1)).getAllBuses();
    }

    @Test
    void shouldGetMappingOfBus() throws Exception {
        when(busService.getBusById(bus.getId())).thenReturn(bus);
        mockMvc.perform(get("/marsoul/api/v1/buses").
                contentType(MediaType.APPLICATION_JSON).
                content(asJsonString(busDTO))).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andDo(MockMvcResultHandlers.print());
    }

    @Test
    void shouldPostMappingOfBus() throws Exception {
        when(busService.saveBus(any())).thenReturn(bus);
        when(modelMapper.map(busDTO, Bus.class)).thenReturn(bus);
        mockMvc.perform(post("/marsoul/api/v1/buses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(busDTO)))
                .andExpect(status().isCreated());

        verify(busService, times(1)).saveBus(any());
    }

    @Test
    void shouldDeleteMappingOfBus() throws Exception {
        mockMvc.perform(delete("/marsoul/api/v1/buses/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(busDTO)))
                .andExpect(MockMvcResultMatchers.status().isNoContent()).
                andDo(MockMvcResultHandlers.print());
    }

    String asJsonString(final BusDTO obj){
        try{
            return new ObjectMapper().writeValueAsString(obj);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

}