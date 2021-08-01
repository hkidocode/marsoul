package ma.youcode.marsoul.service;

import ma.youcode.marsoul.entity.User;
import ma.youcode.marsoul.exception.PersonExistException;
import ma.youcode.marsoul.exception.PersonNotExistException;
import ma.youcode.marsoul.repository.PersonRepository;
import ma.youcode.marsoul.service.impl.PersonService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public User getPersonById(Long personId) {
        return personRepository.findById(personId)
                .orElseThrow(() -> new PersonNotExistException("Person does not exist"));
    }

    @Override
    public List<User> getAllPersons() {
        return personRepository.findAll();
    }

    @Override
    public User savePerson(User user) {
        Optional<User> personByEmail = personRepository.findPersonByEmail(user.getEmail());
        if (personByEmail.isPresent()) {
            throw new PersonExistException("Person already exist");
        }
        return personRepository.save(user);
    }

    @Override
    public User updatePerson(Long personId, User user) {
        User targetedUser = getPersonById(personId);
        targetedUser.setFirstName(user.getFirstName());
        targetedUser.setLastName(user.getLastName());
        targetedUser.setRole(user.getRole());
        targetedUser.setPhone(user.getPhone());
        targetedUser.setEmail(user.getEmail());
        targetedUser.setPassword(user.getPassword());
        targetedUser.setUpdatedAt(new Date(System.currentTimeMillis()));
        return personRepository.save(targetedUser);
    }

    @Override
    public void deletePersonById(Long personId) {
        if (personRepository.findById(personId).isPresent()) {
            personRepository.deleteById(personId);
        } else {
            throw new PersonNotExistException("Person does not exist");
        }
    }
}
