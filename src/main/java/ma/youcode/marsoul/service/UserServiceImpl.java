package ma.youcode.marsoul.service;

import ma.youcode.marsoul.entity.Role;
import ma.youcode.marsoul.entity.User;
import ma.youcode.marsoul.exception.UserExistException;
import ma.youcode.marsoul.exception.UserNotExistException;
import ma.youcode.marsoul.repository.RoleRepository;
import ma.youcode.marsoul.repository.UserRepository;
import ma.youcode.marsoul.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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
    public User saveUser(User user) {
        Optional<User> personByEmail = userRepository.findPersonByEmail(user.getEmail());
        if (personByEmail.isPresent()) {
            throw new UserExistException("User already exist");
        }
        return userRepository.save(user);
    }

    @Override
    public void saveRoleToUser(String email, String roleName) {
        User user = userRepository.findByEmail(email);
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
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
