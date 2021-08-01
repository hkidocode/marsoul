package ma.youcode.marsoul.controller;

import ma.youcode.marsoul.dto.RoleDTO;
import ma.youcode.marsoul.entity.Role;
import ma.youcode.marsoul.service.impl.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@CrossOrigin
@RestController
@RequestMapping("/marsoul/api/v1/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<RoleDTO> getAllRoles() {
        Collection<Role> allRoles = roleService.getAllRoles();
        return ResponseEntity.ok().body(modelMapper.map(allRoles, RoleDTO.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDTO> getRole(@PathVariable("id") Integer id) {
        Role role = roleService.getRoleById(id);
        return ResponseEntity.ok().body(modelMapper.map(role, RoleDTO.class));
    }

    @PostMapping
    public ResponseEntity<Role> addRole(@RequestBody RoleDTO roleDTO) {
        Role role = roleService.saveRole(modelMapper.map(roleDTO, Role.class));
        return ResponseEntity.status(HttpStatus.CREATED).body(role);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Role> updateRole(@PathVariable("id") Integer id, @RequestBody RoleDTO roleDTO) {
        Role role = roleService.updateRole(id, modelMapper.map(roleDTO, Role.class));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(role);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteRole(@PathVariable("id") Integer id) {
        roleService.deleteRoleById(id);
        return ResponseEntity.noContent().build();
    }

}
