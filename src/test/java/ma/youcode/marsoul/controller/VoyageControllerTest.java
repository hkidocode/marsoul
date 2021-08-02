package ma.youcode.marsoul.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ma.youcode.marsoul.dto.VoyageDTO;
import ma.youcode.marsoul.entity.Voyage;
import ma.youcode.marsoul.enums.VoyageStatus;
import ma.youcode.marsoul.service.VoyageServiceImpl;
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
class VoyageControllerTest {

    @MockBean
    private VoyageServiceImpl voyageService;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ModelMapper modelMapper;

    Voyage voyage = new Voyage(1L, 23, VoyageStatus.PENDING);

    VoyageDTO voyageDTO = new VoyageDTO(23, VoyageStatus.PENDING);


    @Test
    void shouldGetMappingOfAllVoyages() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/marsoul/api/v1/voyages").
                contentType(MediaType.APPLICATION_JSON).
                content(asJsonString(voyageDTO))).
                andDo(MockMvcResultHandlers.print());
        verify(voyageService).getAllVoyages();
        verify(voyageService,times(1)).getAllVoyages();
    }

    @Test
    void shouldGetMappingOfVoyage() throws Exception {
        when(voyageService.getVoyageById(voyage.getId())).thenReturn(voyage);
        mockMvc.perform(get("/marsoul/api/v1/voyages").
                contentType(MediaType.APPLICATION_JSON).
                content(asJsonString(voyageDTO))).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andDo(MockMvcResultHandlers.print());
    }

    @Test
    void shouldPostMappingOfVoyage() throws Exception {
        when(voyageService.saveVoyage(any())).thenReturn(voyage);
        when(modelMapper.map(voyageDTO, Voyage.class)).thenReturn(voyage);
        mockMvc.perform(post("/marsoul/api/v1/voyages")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(voyageDTO)))
                .andExpect(status().isCreated());

        verify(voyageService, times(1)).saveVoyage(any());
    }

    @Test
    void shouldDeleteMappingOfVoyage() throws Exception {
        mockMvc.perform(delete("/marsoul/api/v1/voyages/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(voyageDTO)))
                .andExpect(MockMvcResultMatchers.status().isNoContent()).
                andDo(MockMvcResultHandlers.print());
    }

    String asJsonString(final VoyageDTO obj){
        try{
            return new ObjectMapper().writeValueAsString(obj);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

}