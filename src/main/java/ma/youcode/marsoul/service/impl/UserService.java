package ma.youcode.marsoul.service.impl;

import ma.youcode.marsoul.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    User getUserById(Long personId);
    List<User> getAllUsers();
    User saveUser(User user);
    User updateUser(Long personId, User user);
    void deleteUserById(Long personId);

}
