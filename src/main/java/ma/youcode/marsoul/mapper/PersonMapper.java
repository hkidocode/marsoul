package ma.youcode.marsoul.mapper;

import ma.youcode.marsoul.dto.PersonDTO;
import ma.youcode.marsoul.entity.Person;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    PersonDTO entityToDTO(Person person);
    List<PersonDTO> entitiesToDTOs(List<Person> people);

    @InheritInverseConfiguration
    Person dtoToEntity(PersonDTO personDTO);
    List<Person> dtosToEntities(List<PersonDTO> personDTOS);

}
