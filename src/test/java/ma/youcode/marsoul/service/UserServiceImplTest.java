package ma.youcode.marsoul.service;

import ma.youcode.marsoul.entity.User;
import ma.youcode.marsoul.repository.RoleRepository;
import ma.youcode.marsoul.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private UserServiceImpl userService;

    User user1 = new User(1L, "Mustapha", "Kadouri", "21260000000", "test1@gmail.com", "1234");

    User user2 = new User(2L, "Asma", "Kadouri", "212600948000", "test2@gmail.com", "123466");

    List<User> users = new ArrayList<>();

    @Test
    void shouldReturnSavedUserEmail() {

        when(userRepository.save(any(User.class))).thenReturn(user1);
//        when(roleRepository.findAll()).thenReturn(rolesNames);
//        User savedUser = userService.saveVoyage(user1);
//        assertThat(savedUser.getEmail()).isEqualTo("test1@gmail.com");
    }

    @Test
    void shouldReturnUserOfIdOne() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));
        assertThat(userService.getUserById(user1.getId())).isEqualTo(user1);
    }

    @Test
    void shouldReturnListOfUsers() {
        users.add(user1);
        users.add(user2);
        userRepository.save(user1);
        when(userRepository.findAll()).thenReturn(users);
        List<User> userList = userService.getAllUsers();
        assertEquals(users, userList);
        verify(userRepository, times(1)).save(user1);
        verify(userRepository, times(1)).findAll();
    }

}