package ma.youcode.marsoul.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ma.youcode.marsoul.dto.RoleDTO;
import ma.youcode.marsoul.entity.Role;
import ma.youcode.marsoul.service.impl.RoleServiceImpl;
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
class RoleControllerTest {

    @MockBean
    private RoleServiceImpl roleService;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ModelMapper modelMapper;

    Role role = new Role(1, "ROLE_USER");;

    RoleDTO roleDTO = new RoleDTO("ROLE_USER");

//
//    @Test
//    void shouldGetMappingOfAllRoles() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/marsoul/api/v1/roles").
//                contentType(MediaType.APPLICATION_JSON).
//                content(asJsonString(roleDTO))).
//                andDo(MockMvcResultHandlers.print());
//        verify(roleService).getAllRoles();
//        verify(roleService,times(1)).getAllRoles();
//    }

    @Test
    void shouldGetMappingOfRole() throws Exception {
        when(roleService.getRoleById(role.getId())).thenReturn(role);
        mockMvc.perform(get("/marsoul/api/v1/roles").
                contentType(MediaType.APPLICATION_JSON).
                content(asJsonString(roleDTO))).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andDo(MockMvcResultHandlers.print());
    }

    @Test
    void shouldPostMappingOfRole() throws Exception {
        when(roleService.saveRole(any())).thenReturn(role);
        when(modelMapper.map(roleDTO, Role.class)).thenReturn(role);
        mockMvc.perform(post("/marsoul/api/v1/roles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(roleDTO)))
                .andExpect(status().isCreated());

        verify(roleService, times(1)).saveRole(any());
    }

    @Test
    void shouldDeleteMappingOfRole() throws Exception {
        mockMvc.perform(delete("/marsoul/api/v1/roles/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(roleDTO)))
                .andExpect(MockMvcResultMatchers.status().isNoContent()).
                andDo(MockMvcResultHandlers.print());
    }

    String asJsonString(final RoleDTO obj){
        try{
            return new ObjectMapper().writeValueAsString(obj);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

}