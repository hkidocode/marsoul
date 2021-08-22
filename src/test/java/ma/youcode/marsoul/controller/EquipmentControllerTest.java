package ma.youcode.marsoul.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ma.youcode.marsoul.dto.EquipmentDTO;
import ma.youcode.marsoul.entity.Equipment;
import ma.youcode.marsoul.service.impl.EquipmentServiceImpl;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EquipmentControllerTest {

    @MockBean
    private EquipmentServiceImpl busService;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ModelMapper modelMapper;

    Equipment equipment = new Equipment(1, "Wifi");

    EquipmentDTO equipmentDTO = new EquipmentDTO("Wifi");


    @Test
    void shouldGetMappingOfAllEquipments() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/marsoul/api/v1/equipments").
                contentType(MediaType.APPLICATION_JSON).
                content(asJsonString(equipmentDTO))).
                andDo(MockMvcResultHandlers.print());
        verify(busService).getAllEquipments();
        verify(busService,times(1)).getAllEquipments();
    }

    @Test
    void shouldGetMappingOfEquipment() throws Exception {
        when(busService.getEquipmentById(equipment.getId())).thenReturn(equipment);
        mockMvc.perform(get("/marsoul/api/v1/equipments").
                contentType(MediaType.APPLICATION_JSON).
                content(asJsonString(equipmentDTO))).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andDo(MockMvcResultHandlers.print());
    }

    @Test
    void shouldPostMappingOfEquipment() throws Exception {
        when(busService.saveEquipment(any())).thenReturn(equipment);
        when(modelMapper.map(equipmentDTO, Equipment.class)).thenReturn(equipment);
        mockMvc.perform(post("/marsoul/api/v1/equipments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(equipmentDTO)))
                .andExpect(status().isCreated());

        verify(busService, times(1)).saveEquipment(any());
    }

    @Test
    void shouldDeleteMappingOfEquipment() throws Exception {
        mockMvc.perform(delete("/marsoul/api/v1/equipments/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(equipmentDTO)))
                .andExpect(MockMvcResultMatchers.status().isNoContent()).
                andDo(MockMvcResultHandlers.print());
    }

    String asJsonString(final EquipmentDTO obj){
        try{
            return new ObjectMapper().writeValueAsString(obj);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

}