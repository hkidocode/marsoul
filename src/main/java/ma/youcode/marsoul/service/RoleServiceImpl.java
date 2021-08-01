package ma.youcode.marsoul.service;

import ma.youcode.marsoul.entity.Role;
import ma.youcode.marsoul.exception.RoleExistException;
import ma.youcode.marsoul.exception.RoleNotExistException;
import ma.youcode.marsoul.repository.RoleRepository;
import ma.youcode.marsoul.service.impl.RoleService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role getRoleById(Integer roleId) {
        return roleRepository.findById(roleId)
                .orElseThrow(() -> new RoleNotExistException("Role does not exist"));
    }

    @Override
    public Collection<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role saveRole(Role role) {
        Optional<Role> roleByName = roleRepository.findRoleByName(role.getName());
        if (roleByName.isPresent()) {
            throw new RoleExistException("Role already exist");
        }
        return roleRepository.save(role);
    }

    @Override
    public Role updateRole(Integer roleId, Role role) {
        Role targetedRole = getRoleById(roleId);
        targetedRole.setName(role.getName());
        targetedRole.setUpdatedAt(new Date(System.currentTimeMillis()));
        return roleRepository.save(role);
    }

    @Override
    public void deleteRoleById(Integer roleId) {
        if (roleRepository.findById(roleId).isPresent()) {
            roleRepository.deleteById(roleId);
        } else {
            throw new RoleNotExistException("Role does not exist");
        }
    }
}
