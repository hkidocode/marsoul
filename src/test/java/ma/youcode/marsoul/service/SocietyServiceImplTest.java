package ma.youcode.marsoul.service;

import ma.youcode.marsoul.entity.Society;
import ma.youcode.marsoul.repository.SocietyRepository;
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
class SocietyServiceImplTest {

    @Mock
    private SocietyRepository societyRepository;

    @InjectMocks
    private SocietyServiceImpl societyService;

    Society society1 = new Society(1, "CTM", 40);

    Society society2 = new Society(2, "Supra Tours", 30);

    List<Society> societies = new ArrayList<>();

    @Test
    void shouldReturnSavedSocietyName() {
        when(societyRepository.save(any(Society.class))).thenReturn(society1);
        Society savedSociety = societyService.saveSociety(society1);
        assertThat(savedSociety.getName()).isEqualTo("CTM");
    }

    @Test
    void shouldReturnSocietyOfIdOne() {
        when(societyRepository.findById(1)).thenReturn(Optional.of(society1));
        assertThat(societyService.getSocietyById(society1.getId())).isEqualTo(society1);
    }

    @Test
    void shouldReturnListOfSocieties() {
        societies.add(society1);
        societies.add(society2);
        societyRepository.save(society1);
        when(societyRepository.findAll()).thenReturn(societies);
        List<Society> societyList = societyService.getAllSocieties();
        assertEquals(societies, societyList);
        verify(societyRepository, times(1)).save(society1);
        verify(societyRepository, times(1)).findAll();
    }

}