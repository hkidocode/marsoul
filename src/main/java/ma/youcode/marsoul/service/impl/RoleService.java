package ma.youcode.marsoul.service.impl;

import ma.youcode.marsoul.entity.Role;
import org.springframework.stereotype.Service;

import java.util.Collection;

public interface RoleService {
    Role getRoleById(Integer roleId);
    Collection<Role> getAllRoles();
    Role saveRole(Role role);
    Role updateRole(Integer roleId, Role role);
    void deleteRoleById(Integer roleId);
}
