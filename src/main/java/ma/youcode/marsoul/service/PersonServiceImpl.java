package ma.youcode.marsoul.service;

import ma.youcode.marsoul.entity.Person;
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
    public Person getPersonById(Long personId) {
        return personRepository.findById(personId)
                .orElseThrow(() -> new PersonNotExistException("Person does not exist"));
    }

    @Override
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    @Override
    public Person savePerson(Person person) {
        Optional<Person> personByEmail = personRepository.findPersonByEmail(person.getEmail());
        if (personByEmail.isPresent()) {
            throw new PersonExistException("Person already exist");
        }
        return personRepository.save(person);
    }

    @Override
    public Person updatePerson(Long personId, Person person) {
        Person targetedPerson = getPersonById(personId);
        targetedPerson.setFirstName(person.getFirstName());
        targetedPerson.setLastName(person.getLastName());
        targetedPerson.setRole(person.getRole());
        targetedPerson.setPhone(person.getPhone());
        targetedPerson.setEmail(person.getEmail());
        targetedPerson.setPassword(person.getPassword());
        targetedPerson.setUpdatedAt(new Date(System.currentTimeMillis()));
        return personRepository.save(targetedPerson);
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
