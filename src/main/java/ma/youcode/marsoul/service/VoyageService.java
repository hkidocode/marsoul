package ma.youcode.marsoul.service;

import ma.youcode.marsoul.entity.Voyage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VoyageService {
    Voyage getVoyageById(Long voyageId);
    Page<Voyage> getAllVoyages(Pageable pageable);
    Voyage saveVoyage(Voyage voyage);
    Voyage updateVoyage(Long voyageId, Voyage voyage);
    void deleteVoyageById(Long voyageId);
}
