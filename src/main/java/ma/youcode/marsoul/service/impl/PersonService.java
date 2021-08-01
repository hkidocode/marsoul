package ma.youcode.marsoul.service.impl;

import ma.youcode.marsoul.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PersonService {
    User getPersonById(Long personId);
    List<User> getAllPersons();
    User savePerson(User user);
    User updatePerson(Long personId, User user);
    void deletePersonById(Long personId);

}
