package ma.youcode.marsoul.service;

import ma.youcode.marsoul.entity.Role;
import ma.youcode.marsoul.repository.RoleRepository;
import ma.youcode.marsoul.service.impl.RoleServiceImpl;
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
class RoleServiceImplTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    Role role1 = new Role(1, "ROLE_USER");

    Role role2 = new Role(2, "ROLE_ADMIN");

    List<Role> roles = new ArrayList<>();

    @Test
    void shouldReturnSavedRoleName() {
        when(roleRepository.save(any(Role.class))).thenReturn(role1);
        Role savedRole = roleService.saveRole(role1);
        assertThat(savedRole.getName()).isEqualTo("ROLE_USER");
    }

    @Test
    void shouldReturnRoleOfIdOne() {
        when(roleRepository.findById(1)).thenReturn(Optional.of(role1));
        assertThat(roleService.getRoleById(role1.getId())).isEqualTo(role1);
    }

    @Test
    void shouldReturnListOfRoles() {
        roles.add(role1);
        roles.add(role2);
        roleRepository.save(role1);
        when(roleRepository.findAll()).thenReturn(roles);
        Collection<Role> roleCollection = roleService.getAllRoles();
        assertEquals(roles, roleCollection);
        verify(roleRepository, times(1)).save(role1);
        verify(roleRepository, times(1)).findAll();
    }

}