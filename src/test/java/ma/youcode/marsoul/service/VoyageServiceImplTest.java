package ma.youcode.marsoul.service;

import ma.youcode.marsoul.entity.Voyage;
import ma.youcode.marsoul.enums.VoyageStatus;
import ma.youcode.marsoul.repository.VoyageRepository;
import ma.youcode.marsoul.service.impl.VoyageServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VoyageServiceImplTest {

    @Mock
    private VoyageRepository voyageRepository;

    @InjectMocks
    private VoyageServiceImpl voyageService;

    Voyage voyage1 = new Voyage(1L, 23, VoyageStatus.PENDING, new Date());

    Voyage voyage2 = new Voyage(2L, 12, VoyageStatus.PAID, new Date());

    List<Voyage> voyages = new ArrayList<>();

    @Test
    void shouldReturnSavedVoyageSeatPosition() {
        when(voyageRepository.save(any(Voyage.class))).thenReturn(voyage1);
        Voyage savedVoyage = voyageService.saveVoyage(voyage1);
        assertThat(savedVoyage.getSeatPosition()).isEqualTo(23);
    }

    @Test
    void shouldReturnVoyageOfIdOne() {
        when(voyageRepository.findById(1L)).thenReturn(Optional.of(voyage1));
        assertThat(voyageService.getVoyageById(voyage1.getId())).isEqualTo(voyage1);
    }

//    @Test
//    void shouldReturnListOfVoyages() {
//        voyages.add(voyage1);
//        voyages.add(voyage2);
//        voyageRepository.save(voyage1);
//        when(voyageRepository.findAll()).thenReturn(voyages);
//        List<Voyage> voyageList = voyageService.getAllVoyages();
//        assertEquals(voyages, voyageList);
//        verify(voyageRepository, times(1)).save(voyage1);
//        verify(voyageRepository, times(1)).findAll();
//    }

}