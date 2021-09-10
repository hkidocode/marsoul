package ma.youcode.marsoul.service.impl;

import ma.youcode.marsoul.entity.Role;
import ma.youcode.marsoul.exception.EntityExistException;
import ma.youcode.marsoul.exception.EntityNotExistException;
import ma.youcode.marsoul.repository.RoleRepository;
import ma.youcode.marsoul.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role getRoleById(Integer roleId) {
        return roleRepository.findById(roleId)
                .orElseThrow(() -> new EntityNotExistException("Role does not exist"));
    }

    @Override
    public Collection<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role saveRole(Role role) {
        Optional<Role> roleByName = roleRepository.findRoleByName(role.getName());
        if (roleByName.isPresent()) {
            throw new EntityExistException("Role already exist");
        }
        return roleRepository.save(role);
    }

    @Override
    public Role updateRole(Integer roleId, Role role) {
        Role targetedRole = getRoleById(roleId);
        targetedRole.setName(role.getName());
        return roleRepository.save(role);
    }

    @Override
    public void deleteRoleById(Integer roleId) {
        if (roleRepository.findById(roleId).isPresent()) {
            roleRepository.deleteById(roleId);
        } else {
            throw new EntityNotExistException("Role does not exist");
        }
    }
}
