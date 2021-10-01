package ma.youcode.marsoul.controller;

import ma.youcode.marsoul.dto.RoleDTO;
import ma.youcode.marsoul.entity.Role;
import ma.youcode.marsoul.service.RoleService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/marsoul/api/v1/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<Page<RoleDTO>> getAllRoles(@RequestParam(name = "page", required = false, defaultValue = "0") int page) {
        Pageable pagingSort = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "id"));
        Page<Role> allRoles = roleService.getAllRoles(pagingSort);
        return ResponseEntity.ok().body(modelMapper.map(allRoles, new TypeToken<Page<RoleDTO>>(){}.getType()));
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
