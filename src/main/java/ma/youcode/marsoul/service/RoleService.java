package ma.youcode.marsoul.service;

import ma.youcode.marsoul.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoleService {
    Role getRoleById(Integer roleId);
    Page<Role> getAllRoles(Pageable pageable);
    Role saveRole(Role role);
    Role updateRole(Integer roleId, Role role);
    void deleteRoleById(Integer roleId);
}
