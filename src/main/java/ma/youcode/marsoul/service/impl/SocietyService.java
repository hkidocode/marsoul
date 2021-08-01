package ma.youcode.marsoul.service.impl;

import ma.youcode.marsoul.entity.Society;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SocietyService {
    Society getSocietyById(Integer societyId);
    List<Society> getAllSocieties();
    Society saveSociety(Society society);
    Society updateSociety(Integer societyId, Society society);
    void deleteSocietyById(Integer societyId);
}
