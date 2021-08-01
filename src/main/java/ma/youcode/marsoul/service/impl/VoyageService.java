package ma.youcode.marsoul.service.impl;

import ma.youcode.marsoul.entity.Voyage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface VoyageService {
    Voyage getVoyageById(Long voyageId);
    List<Voyage> getAllVoyages();
    Voyage saveVoyage(Voyage voyage);
    Voyage updateVoyage(Voyage voyage, Long voyageId);
    void deleteVoyageById(Long voyageId);
}
