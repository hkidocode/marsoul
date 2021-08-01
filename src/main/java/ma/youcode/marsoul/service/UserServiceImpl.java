package ma.youcode.marsoul.service;

import ma.youcode.marsoul.entity.User;
import ma.youcode.marsoul.exception.PersonExistException;
import ma.youcode.marsoul.exception.PersonNotExistException;
import ma.youcode.marsoul.repository.UserRepository;
import ma.youcode.marsoul.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUserById(Long personId) {
        return userRepository.findById(personId)
                .orElseThrow(() -> new PersonNotExistException("Person does not exist"));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User saveUser(User user) {
        Optional<User> personByEmail = userRepository.findPersonByEmail(user.getEmail());
        if (personByEmail.isPresent()) {
            throw new PersonExistException("Person already exist");
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
            throw new PersonNotExistException("Person does not exist");
        }
    }
}
