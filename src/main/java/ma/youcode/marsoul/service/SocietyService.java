package ma.youcode.marsoul.service;

import ma.youcode.marsoul.entity.Society;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SocietyService {
    Society getSocietyById(Long societyId);
    Page<Society> getAllSocieties(Pageable pageable);
    Society saveSociety(Society society);
    Society updateSociety(Long societyId, Society society);
    void deleteSocietyById(Long societyId);
}
