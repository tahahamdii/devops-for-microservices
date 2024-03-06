package tn.esprit.brogram.backend.services;

import tn.esprit.brogram.backend.dao.entities.Foyer;

import java.util.List;

public interface IFoyerService {
    Foyer addFoyer(Foyer f, String name);
    List<Foyer> addAllFoyer(List<Foyer> ls);
    Foyer editFoyer(Foyer f);
    List<Foyer> findAllFoyer();
    Foyer findByIDFoyer(long id);
    void deleteByIDFoyer(long id);
    List<Foyer> findFoyerByUniversersite(String nom);
    void deleteFoyer(Foyer f);
}
