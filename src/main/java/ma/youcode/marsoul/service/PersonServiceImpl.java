package ma.youcode.marsoul.service;

import ma.youcode.marsoul.entity.Person;
import ma.youcode.marsoul.exception.PersonNotExistException;
import ma.youcode.marsoul.repository.PersonRepository;
import ma.youcode.marsoul.service.impl.PersonService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public Person getPersonById(Long personId) {
        return personRepository.findById(personId)
                .orElseThrow(() -> new PersonNotExistException("Person entity does not exist"));
    }

    @Override
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    @Override
    public Person savePerson(Person person) {
        return personRepository.save(person);
    }

    @Override
    public Person updatePerson(Long personId, Person person) {
        return personRepository.save(person);
    }

    @Override
    public void deletePersonById(Long personId) {
        if (personRepository.findById(personId).isPresent()) {
            personRepository.deleteById(personId);
        } else {
            throw new PersonNotExistException("Person entity does not exist");
        }
    }
}
