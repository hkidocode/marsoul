package ma.youcode.marsoul.controller;


import ma.youcode.marsoul.dto.PersonDTO;
import ma.youcode.marsoul.entity.Person;
import ma.youcode.marsoul.mapper.PersonMapper;
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

    @Autowired
    private PersonMapper personMapper;

    @GetMapping
    public ResponseEntity<List<PersonDTO>> getAllPersons() {
        return ResponseEntity.status(HttpStatus.OK).body(personMapper.entitiesToDTOs(personService.getAll()));
    }

    @GetMapping("{personId}")
    public ResponseEntity<PersonDTO> getPerson(@PathVariable Long personId) {
        return ResponseEntity.status(HttpStatus.OK).body(personMapper.entityToDTO(personService.getById(personId)));
    }

    @PostMapping
    public ResponseEntity<Person> addPerson(@RequestBody @Valid PersonDTO personDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(personService.addOrUpdate(personMapper.dtoToEntity(personDTO)));
    }

    @PutMapping("{personId}")
    public ResponseEntity<Person> updatePerson(@PathVariable Long personId, @RequestBody @Valid Person person) {
        Person targetedPerson = personService.getById(personId);
        targetedPerson.setFirstName(person.getFirstName());
        targetedPerson.setLastName(person.getLastName());
        targetedPerson.setRole(person.getRole());
        targetedPerson.setPhone(person.getPhone());
        targetedPerson.setEmail(person.getEmail());
        targetedPerson.setPassword(person.getPassword());

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(personService.addOrUpdate(targetedPerson));

    }

    @DeleteMapping("{personId}")
    public ResponseEntity<HttpStatus> deletePerson(@PathVariable Long personId) {
        personService.deleteById(personId);
        return ResponseEntity.noContent().build();
    }
}
