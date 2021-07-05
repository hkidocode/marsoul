package ma.youcode.marsoul.mapper;

import ma.youcode.marsoul.dto.EquipmentDTO;
import ma.youcode.marsoul.entity.Equipment;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

// generate BusMapper implementation class
@Mapper(componentModel = "spring") // componentModel add @Component annotation to BusMapperImpl
public interface EquipmentMapper {

    EquipmentDTO entityToDTO(Equipment equipment);
    List<EquipmentDTO> entitiesToDTOs(List<Equipment> equipments);

    @InheritInverseConfiguration
    Equipment dtoToEntity(EquipmentDTO equipmentDTO);
    List<Equipment> dtosToEntities(List<EquipmentDTO> equipmentDTOS);

}