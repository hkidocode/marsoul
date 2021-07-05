package ma.youcode.marsoul.service;

import ma.youcode.marsoul.entity.Voyage;
import ma.youcode.marsoul.exception.VoyageNotExistException;
import ma.youcode.marsoul.repository.VoyageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoyageService {

    @Autowired
    private VoyageRepository voyageRepository;

    public Voyage getById(Long voyageId) {
        return voyageRepository.findById(voyageId)
                .orElseThrow(() -> new VoyageNotExistException("Voyage entity does not exist"));
    }

    public List<Voyage> getAll() {
        return voyageRepository.findAll();
    }

    public Voyage addOrUpdate(Voyage voyage) {
        return voyageRepository.save(voyage);
    }

    public void deleteById(Long voyageId) {
        if (voyageRepository.findById(voyageId).isPresent()) {
            voyageRepository.deleteById(voyageId);
        } else {
            throw new VoyageNotExistException("Voyage entity does not exist");
        }
    }
}
