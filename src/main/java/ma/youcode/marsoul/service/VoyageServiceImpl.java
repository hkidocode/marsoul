package ma.youcode.marsoul.service;

import ma.youcode.marsoul.entity.Voyage;
import ma.youcode.marsoul.exception.VoyageNotExistException;
import ma.youcode.marsoul.repository.VoyageRepository;
import ma.youcode.marsoul.service.impl.VoyageService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class VoyageServiceImpl implements VoyageService {

    @Autowired
    private VoyageRepository voyageRepository;

    @Override
    public Voyage getVoyageById(Long voyageId) {
        return voyageRepository.findById(voyageId)
                .orElseThrow(() -> new VoyageNotExistException("Voyage entity does not exist"));
    }

    @Override
    public List<Voyage> getAllVoyages() {
        return voyageRepository.findAll();
    }

    @Override
    public Voyage saveVoyage(Voyage voyage) {
        return voyageRepository.save(voyage);
    }

    @Override
    public Voyage updateVoyage(Voyage voyage, Long voyageId) {
        return voyageRepository.save(voyage);
    }

    @Override
    public void deleteVoyageById(Long voyageId) {
        if (voyageRepository.findById(voyageId).isPresent()) {
            voyageRepository.deleteById(voyageId);
        } else {
            throw new VoyageNotExistException("Voyage entity does not exist");
        }
    }
}
