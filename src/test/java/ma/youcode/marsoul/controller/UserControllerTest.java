package ma.youcode.marsoul.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ma.youcode.marsoul.dto.UserDTO;
import ma.youcode.marsoul.entity.User;
import ma.youcode.marsoul.service.impl.UserServiceImpl;
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
class UserControllerTest {

    @MockBean
    private UserServiceImpl userService;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ModelMapper modelMapper;

    User user = new User(1L, "Mustapha", "Kadouri", "21260000000", "test1@gmail.com", "1234");

    UserDTO userDTO =  new UserDTO("Mustapha", "Kadouri", "https://google.com/user", "test1@gmail.com", "1234");


    @Test
    void shouldGetMappingOfAllUsers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/marsoul/api/v1/users").
                contentType(MediaType.APPLICATION_JSON).
                content(asJsonString(userDTO))).
                andDo(MockMvcResultHandlers.print());
        verify(userService).getAllUsers();
        verify(userService,times(1)).getAllUsers();
    }

    @Test
    void shouldGetMappingOfUser() throws Exception {
        when(userService.getUserById(user.getId())).thenReturn(user);
        mockMvc.perform(get("/marsoul/api/v1/users").
                contentType(MediaType.APPLICATION_JSON).
                content(asJsonString(userDTO))).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andDo(MockMvcResultHandlers.print());
    }

    @Test
    void shouldPostMappingOfUser() throws Exception {
        when(userService.saveUser(any(), any())).thenReturn(user);
        when(modelMapper.map(userDTO, User.class)).thenReturn(user);
        mockMvc.perform(post("/marsoul/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userDTO)))
                .andExpect(status().isCreated());

        verify(userService, times(1)).saveUser(any(), any());
    }

    @Test
    void shouldDeleteMappingOfUser() throws Exception {
        mockMvc.perform(delete("/marsoul/api/v1/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userDTO)))
                .andExpect(MockMvcResultMatchers.status().isNoContent()).
                andDo(MockMvcResultHandlers.print());
    }

    String asJsonString(final UserDTO obj){
        try{
            return new ObjectMapper().writeValueAsString(obj);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

}