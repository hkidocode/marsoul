package ma.youcode.marsoul.service;

import ma.youcode.marsoul.entity.Person;
import ma.youcode.marsoul.entity.Society;
import ma.youcode.marsoul.exception.PersonNotExistException;
import ma.youcode.marsoul.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public Person getById(Long personId) {
        if (personRepository.findById(personId).isPresent()) {
            return personRepository.findById(personId).get();
        } else {
            throw new PersonNotExistException("Person entity does not exist");
        }
    }

    public List<Person> getAll() {
        return personRepository.findAll();
    }

    public Person addOrUpdate(Person person) {
        return personRepository.save(person);
    }

    public void deleteById(Long personId) {
        if (personRepository.findById(personId).isPresent()) {
            personRepository.deleteById(personId);
        } else {
            throw new PersonNotExistException("Person entity does not exist");
        }
    }
}
