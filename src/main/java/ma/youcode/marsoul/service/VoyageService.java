package ma.youcode.marsoul.service;

import ma.youcode.marsoul.entity.Voyage;

import java.util.List;

public interface VoyageService {
    Voyage getVoyageById(Long voyageId);
    List<Voyage> getAllVoyages();
    Voyage saveVoyage(Voyage voyage);
    Voyage updateVoyage(Long voyageId, Voyage voyage);
    void deleteVoyageById(Long voyageId);
}
