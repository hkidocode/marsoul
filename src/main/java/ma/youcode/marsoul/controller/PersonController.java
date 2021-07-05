package ma.youcode.marsoul.controller;


import ma.youcode.marsoul.entity.Person;
import ma.youcode.marsoul.exception.VoyageNotExistException;
import ma.youcode.marsoul.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/marsoul/api/v1/users")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping
    public ResponseEntity<List<Person>> getAllPersons() {
        return ResponseEntity.status(HttpStatus.OK).body(personService.getAll());
    }

    @GetMapping("{personId}")
    public ResponseEntity<Person> getPerson(@PathVariable Long personId) {
        return ResponseEntity.status(HttpStatus.OK).body(personService.getById(personId));
    }

    @PostMapping
    public ResponseEntity<Person> addPerson(@RequestBody @Valid Person person) {
        return ResponseEntity.status(HttpStatus.CREATED).body(personService.addOrUpdate(person));
    }

    @PutMapping("{personId}")
    public ResponseEntity<Person> updatePerson(@PathVariable Long personId, @RequestBody @Valid Person person) {
        Person targetedPerson = personService.getById(personId);
        if (targetedPerson != null) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(personService.addOrUpdate(person));
        } else {
            throw new VoyageNotExistException("Person entity does not exist");
        }
    }

    @DeleteMapping("{personId}")
    public ResponseEntity<HttpStatus> deletePerson(@PathVariable Long personId) {
        personService.deleteById(personId);
        return ResponseEntity.noContent().build();
    }
}
