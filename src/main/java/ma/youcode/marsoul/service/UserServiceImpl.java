package ma.youcode.marsoul.service;

import ma.youcode.marsoul.entity.Role;
import ma.youcode.marsoul.entity.User;
import ma.youcode.marsoul.exception.RoleNotExistException;
import ma.youcode.marsoul.exception.UserExistException;
import ma.youcode.marsoul.exception.UserNotExistException;
import ma.youcode.marsoul.repository.RoleRepository;
import ma.youcode.marsoul.repository.UserRepository;
import ma.youcode.marsoul.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public User getUserById(Long personId) {
        return userRepository.findById(personId)
                .orElseThrow(() -> new UserNotExistException("User does not exist"));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User saveUser(User user, Collection<String> roleNames) {
        Optional<User> personByEmail = userRepository.findPersonByEmail(user.getEmail());
        Collection<Role> roles = new HashSet<>();
        if (personByEmail.isPresent()) {
            throw new UserExistException("User already exist");
        }
        if (roleNames == null) {
            Role role = roleRepository.findByName("ROLE_USER");
            roles.add(role);
            user.setRoles(roles);
        } else {
            for (String roleName : roleNames) {
                Role role = roleRepository.findByName(roleName);
                if (role == null) {
                    throw new RoleNotExistException(roleName + " does not exist");
                }
                roles.add(role);
                user.setRoles(roles);
            }
        }

        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long personId, User user) {
        User targetedUser = getUserById(personId);
        targetedUser.setFirstName(user.getFirstName());
        targetedUser.setLastName(user.getLastName());
        targetedUser.setPhone(user.getPhone());
        targetedUser.setEmail(user.getEmail());
        targetedUser.setPassword(user.getPassword());
        targetedUser.setUpdatedAt(new Date(System.currentTimeMillis()));
        return userRepository.save(targetedUser);
    }

    @Override
    public void deleteUserById(Long personId) {
        if (userRepository.findById(personId).isPresent()) {
            userRepository.deleteById(personId);
        } else {
            throw new UserNotExistException("User does not exist");
        }
    }
}
