package ma.youcode.marsoul.service.impl;

import ma.youcode.marsoul.entity.Voyage;
import ma.youcode.marsoul.exception.EntityExistException;
import ma.youcode.marsoul.exception.EntityNotExistException;
import ma.youcode.marsoul.repository.VoyageRepository;
import ma.youcode.marsoul.service.VoyageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class VoyageServiceImpl implements VoyageService {

    @Autowired
    private VoyageRepository voyageRepository;

    @Override
    public Voyage getVoyageById(Long voyageId) {
        return voyageRepository.findById(voyageId)
                .orElseThrow(() -> new EntityNotExistException("Voyage does not exist"));
    }

    @Override
    public Page<Voyage> getAllVoyages(Pageable pageable) {
        return voyageRepository.findAll(pageable);
    }

    @Override
    public Voyage saveVoyage(Voyage voyage) {
        Optional<Voyage> voyageBySeatPosition = voyageRepository.findBySeatPosition(voyage.getSeatPosition());
        if (voyageBySeatPosition.isPresent()) {
            throw new EntityExistException("Voyage already exist");
        }
        return voyageRepository.save(voyage);
    }

    @Override
    public Voyage updateVoyage(Long voyageId, Voyage voyage) {
        Voyage targetedVoyage = getVoyageById(voyageId);
        targetedVoyage.setSeatPosition(voyage.getSeatPosition());
        targetedVoyage.setStatus(voyage.getStatus());
        return voyageRepository.save(voyage);
    }

    @Override
    public void deleteVoyageById(Long voyageId) {
        if (voyageRepository.findById(voyageId).isPresent()) {
            voyageRepository.deleteById(voyageId);
        } else {
            throw new EntityNotExistException("Voyage does not exist");
        }
    }
}
