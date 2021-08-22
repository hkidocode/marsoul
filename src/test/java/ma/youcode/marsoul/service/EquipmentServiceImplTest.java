package ma.youcode.marsoul.service;

import ma.youcode.marsoul.entity.Equipment;
import ma.youcode.marsoul.repository.EquipmentRepository;
import ma.youcode.marsoul.service.impl.EquipmentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EquipmentServiceImplTest {

    @Mock
    private EquipmentRepository equipmentRepository;

    @InjectMocks
    private EquipmentServiceImpl equipmentService;

    Equipment equipment1 = new Equipment(1, "Wifi");

    Equipment equipment2 = new Equipment(2, "Climat");

    List<Equipment> equipments = new ArrayList<>();

    @Test
    void shouldReturnSavedEquipmentName() {
        when(equipmentRepository.save(any(Equipment.class))).thenReturn(equipment1);
        Equipment savedEquipment = equipmentService.saveEquipment(equipment1);
        assertThat(savedEquipment.getName()).isEqualTo("Wifi");
    }

    @Test
    void shouldReturnEquipmentOfIdOne() {
        when(equipmentRepository.findById(1)).thenReturn(Optional.of(equipment1));
        assertThat(equipmentService.getEquipmentById(equipment1.getId())).isEqualTo(equipment1);
    }

    @Test
    void shouldReturnListOfEquipments() {
        equipments.add(equipment1);
        equipments.add(equipment2);
        equipmentRepository.save(equipment1);
        when(equipmentRepository.findAll()).thenReturn(equipments);
        List<Equipment> equipmentList = equipmentService.getAllEquipments();
        assertEquals(equipments, equipmentList);
        verify(equipmentRepository, times(1)).save(equipment1);
        verify(equipmentRepository, times(1)).findAll();
    }

}