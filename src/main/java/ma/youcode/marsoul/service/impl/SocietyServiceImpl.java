package ma.youcode.marsoul.service.impl;

import ma.youcode.marsoul.entity.Society;
import ma.youcode.marsoul.exception.EntityExistException;
import ma.youcode.marsoul.exception.EntityNotExistException;
import ma.youcode.marsoul.repository.SocietyRepository;
import ma.youcode.marsoul.service.SocietyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SocietyServiceImpl implements SocietyService {

    @Autowired
    private SocietyRepository societyRepository;

    @Override
    public Society getSocietyById(Long societyId) {
        return societyRepository.findById(societyId)
                .orElseThrow(() -> new EntityNotExistException("Society does not exist"));
    }

    @Override
    public List<Society> getAllSocieties() {
        return societyRepository.findAll();
    }

    @Override
    public Society saveSociety(Society society) {
        Optional<Society> societyByName = societyRepository.findByName(society.getName());
        if (societyByName.isPresent()) {
            throw new EntityExistException("Society already exist");
        }
        return societyRepository.save(society);
    }

    @Override
    public Society updateSociety(Long societyId, Society society) {
        Society targetedSociety = getSocietyById(societyId);
        targetedSociety.setName(society.getName());
        targetedSociety.setBusCount(society.getBusCount());
        targetedSociety.setImage(society.getImage());
        return societyRepository.save(targetedSociety);
    }

    @Override
    public void deleteSocietyById(Long societyId) {
        if (societyRepository.findById(societyId).isPresent()) {
            societyRepository.deleteById(societyId);
        } else {
            throw new EntityNotExistException("Society does not exist");
        }
    }
}
