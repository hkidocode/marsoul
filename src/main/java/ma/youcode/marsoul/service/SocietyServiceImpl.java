package ma.youcode.marsoul.service;

import ma.youcode.marsoul.entity.Society;
import ma.youcode.marsoul.exception.SocietyExistException;
import ma.youcode.marsoul.exception.SocietyNotExistException;
import ma.youcode.marsoul.repository.SocietyRepository;
import ma.youcode.marsoul.service.impl.SocietyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SocietyServiceImpl implements SocietyService {

    @Autowired
    private SocietyRepository societyRepository;

    @Override
    public Society getSocietyById(Integer societyId) {
        return societyRepository.findById(societyId)
                .orElseThrow(() -> new SocietyNotExistException("Society does not exist"));
    }

    @Override
    public List<Society> getAllSocieties() {
        return societyRepository.findAll();
    }

    @Override
    public Society saveSociety(Society society) {
        Optional<Society> societyByName = societyRepository.findSocietyByName(society.getName());
        if (societyByName.isPresent()) {
            throw new SocietyExistException("Society already exist");
        }
        return societyRepository.save(society);
    }

    @Override
    public Society updateSociety(Integer societyId, Society society) {
        Society targetedSociety = getSocietyById(societyId);
        targetedSociety.setName(society.getName());
        targetedSociety.setBusCount(society.getBusCount());
        targetedSociety.setUpdatedAt(new Date(System.currentTimeMillis()));
        return societyRepository.save(targetedSociety);
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
