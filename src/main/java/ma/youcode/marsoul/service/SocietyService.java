package ma.youcode.marsoul.service;

import ma.youcode.marsoul.entity.Society;
import ma.youcode.marsoul.exception.SocietyNotExistException;
import ma.youcode.marsoul.repository.SocietyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SocietyService {

    @Autowired
    private SocietyRepository societyRepository;

    public Society getById(Integer societyId) {
        return societyRepository.findById(societyId)
                .orElseThrow(() -> new SocietyNotExistException("Society entity does not exist"));
    }

    public List<Society> getAll() {
        return societyRepository.findAll();
    }

    public Society addOrUpdate(Society society) {
        return societyRepository.save(society);
    }

    public void deleteById(Integer societyId) {
        if (societyRepository.findById(societyId).isPresent()) {
            societyRepository.deleteById(societyId);
        } else {
            throw new SocietyNotExistException("Society entity does not exist");
        }
    }
}
