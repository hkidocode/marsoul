package ma.youcode.marsoul.service;

import ma.youcode.marsoul.entity.Voyage;
import ma.youcode.marsoul.exception.VoyageExistException;
import ma.youcode.marsoul.exception.VoyageNotExistException;
import ma.youcode.marsoul.repository.VoyageRepository;
import ma.youcode.marsoul.service.impl.VoyageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VoyageServiceImpl implements VoyageService {

    @Autowired
    private VoyageRepository voyageRepository;

    @Override
    public Voyage getVoyageById(Long voyageId) {
        return voyageRepository.findById(voyageId)
                .orElseThrow(() -> new VoyageNotExistException("Voyage does not exist"));
    }

    @Override
    public List<Voyage> getAllVoyages() {
        return voyageRepository.findAll();
    }

    @Override
    public Voyage saveVoyage(Voyage voyage) {
        Optional<Voyage> voyageBySeatPosition = voyageRepository.findVoyageBySeatPosition(voyage.getSeatPosition());
        if (voyageBySeatPosition.isPresent()) {
            throw new VoyageExistException("Voyage already exist");
        }
        return voyageRepository.save(voyage);
    }

    @Override
    public Voyage updateVoyage(Long voyageId, Voyage voyage) {
        Voyage targetedVoyage = getVoyageById(voyageId);
        targetedVoyage.setSeatPosition(voyage.getSeatPosition());
        targetedVoyage.setStatus(voyage.getStatus());
        targetedVoyage.setUpdatedAt(new Date(System.currentTimeMillis()));
        return voyageRepository.save(voyage);
    }

    @Override
    public void deleteVoyageById(Long voyageId) {
        if (voyageRepository.findById(voyageId).isPresent()) {
            voyageRepository.deleteById(voyageId);
        } else {
            throw new VoyageNotExistException("Voyage does not exist");
        }
    }
}
