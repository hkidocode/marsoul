package ma.youcode.marsoul.mapper;

import ma.youcode.marsoul.dto.SocietyDTO;
import ma.youcode.marsoul.entity.Society;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SocietyMapper {

    SocietyDTO entityToDTO(Society society);
    List<SocietyDTO> entitiesToDTOs(List<Society> societies);

    @InheritInverseConfiguration
    Society dtoToEntity(SocietyDTO societyDTO);
    List<Society> dtosToEntities(List<SocietyDTO> societyDTOS);

}
