package ma.youcode.marsoul.service.impl;

import ma.youcode.marsoul.entity.User;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

public interface UserService {
    User getUserById(Long personId);
    List<User> getAllUsers();
    User saveUser(User user, Collection<String> roleNames);
    User updateUser(Long personId, User user);
    void deleteUserById(Long personId);

}
