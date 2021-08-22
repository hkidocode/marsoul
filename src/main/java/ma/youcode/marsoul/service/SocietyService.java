package ma.youcode.marsoul.service;

import ma.youcode.marsoul.entity.Society;

import java.util.List;

public interface SocietyService {
    Society getSocietyById(Long societyId);
    List<Society> getAllSocieties();
    Society saveSociety(Society society);
    Society updateSociety(Long societyId, Society society);
    void deleteSocietyById(Long societyId);
}
