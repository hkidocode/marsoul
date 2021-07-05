package ma.youcode.marsoul.mapper;

import ma.youcode.marsoul.dto.VoyageDTO;
import ma.youcode.marsoul.entity.Voyage;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VoyageMapper {

    VoyageDTO entityToDTO(Voyage voyage);
    List<VoyageDTO> entitiesToDTOs(List<Voyage> voyages);

    @InheritInverseConfiguration
    Voyage dtoToEntity(VoyageDTO voyageDTO);
    List<Voyage> dtosToEntities(List<VoyageDTO> voyageDTOS);

}
