package ma.youcode.marsoul.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ma.youcode.marsoul.dto.SocietyDTO;
import ma.youcode.marsoul.entity.Society;
import ma.youcode.marsoul.service.impl.SocietyServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SocietyControllerTest {

    @MockBean
    private SocietyServiceImpl societyService;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ModelMapper modelMapper;

    Society society = new Society(1L, "CTM", 40, "image");

    SocietyDTO societyDTO = new SocietyDTO("CTM", 40, "image");


//    @Test
//    void shouldGetMappingOfAllSocieties() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/marsoul/api/v1/societies").
//                contentType(MediaType.APPLICATION_JSON).
//                content(asJsonString(societyDTO))).
//                andDo(MockMvcResultHandlers.print());
//        verify(societyService).getAllSocieties();
//        verify(societyService,times(1)).getAllSocieties();
//    }

    @Test
    void shouldGetMappingOfSociety() throws Exception {
        when(societyService.getSocietyById(society.getId())).thenReturn(society);
        mockMvc.perform(get("/marsoul/api/v1/societies").
                contentType(MediaType.APPLICATION_JSON).
                content(asJsonString(societyDTO))).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andDo(MockMvcResultHandlers.print());
    }

    @Test
    void shouldPostMappingOfSociety() throws Exception {
        when(societyService.saveSociety(any())).thenReturn(society);
        when(modelMapper.map(societyDTO, Society.class)).thenReturn(society);
        mockMvc.perform(post("/marsoul/api/v1/societies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(societyDTO)))
                .andExpect(status().isCreated());

        verify(societyService, times(1)).saveSociety(any());
    }

    @Test
    void shouldDeleteMappingOfSociety() throws Exception {
        mockMvc.perform(delete("/marsoul/api/v1/societies/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(societyDTO)))
                .andExpect(MockMvcResultMatchers.status().isNoContent()).
                andDo(MockMvcResultHandlers.print());
    }

    String asJsonString(final SocietyDTO obj){
        try{
            return new ObjectMapper().writeValueAsString(obj);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

}