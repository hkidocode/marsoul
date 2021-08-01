package ma.youcode.marsoul.service;

import ma.youcode.marsoul.entity.Society;
import ma.youcode.marsoul.exception.SocietyNotExistException;
import ma.youcode.marsoul.repository.SocietyRepository;
import ma.youcode.marsoul.service.impl.SocietyService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SocietyServiceImpl implements SocietyService {

    @Autowired
    private SocietyRepository societyRepository;

    @Override
    public Society getSocietyById(Integer societyId) {
        return societyRepository.findById(societyId)
                .orElseThrow(() -> new SocietyNotExistException("Society entity does not exist"));
    }

    @Override
    public List<Society> getAllSocieties() {
        return societyRepository.findAll();
    }

    @Override
    public Society saveSociety(Society society) {
        return societyRepository.save(society);
    }

    @Override
    public Society updateSociety(Integer societyId, Society society) {
        return societyRepository.save(society);
    }

    @Override
    public void deleteSocietyById(Integer societyId) {
        if (societyRepository.findById(societyId).isPresent()) {
            societyRepository.deleteById(societyId);
        } else {
            throw new SocietyNotExistException("Society entity does not exist");
        }
    }
}