package ma.youcode.marsoul.service.impl;

import ma.youcode.marsoul.entity.Person;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PersonService {
    Person getPersonById(Long personId);
    List<Person> getAllPersons();
    Person savePerson(Person person);
    Person updatePerson(Long personId, Person person);
    void deletePersonById(Long personId);

}
