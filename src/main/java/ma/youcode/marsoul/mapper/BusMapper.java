package ma.youcode.marsoul.mapper;

import ma.youcode.marsoul.dto.BusDTO;
import ma.youcode.marsoul.entity.Bus;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

// generate BusMapper implementation class
@Mapper(componentModel = "spring") // componentModel add @Component annotation to BusMapperImpl
public interface BusMapper {
    // access BusManager implementation
    BusMapper INSTANCE = Mappers.getMapper(BusMapper.class);

    BusDTO entityToDTO(Bus bus);
    List<BusDTO> entitiesToDTOs(List<Bus> buses);

    @InheritInverseConfiguration
    Bus dtoToEntity(BusDTO busDTO);
    List<Bus> dtosToEntities(List<BusDTO> busDTO);


}
