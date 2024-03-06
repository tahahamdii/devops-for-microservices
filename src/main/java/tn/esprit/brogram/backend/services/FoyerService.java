package tn.esprit.brogram.backend.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.brogram.backend.dao.entities.Foyer;
import tn.esprit.brogram.backend.dao.entities.Universite;
import tn.esprit.brogram.backend.dao.repositories.FoyerRepository;
import tn.esprit.brogram.backend.dao.repositories.UniversiteRepository;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Service
public class FoyerService implements IFoyerService {
    FoyerRepository foyerRepository;
    UniversiteRepository universiteRepository;

    @Override
    public Foyer addFoyer(Foyer f, String name) {
        Universite u = universiteRepository.findUnBynomUniversite(name);
        u.setFoyer(f);
        f.setCreatedAt(new Date());
        foyerRepository.save(f);
        universiteRepository.save(u);
        return f ;
    }

    @Override
    public List<Foyer> addAllFoyer(List<Foyer> ls) {
        return foyerRepository.saveAll(ls);
    }

    @Override
    public Foyer editFoyer(Foyer f) {
        return foyerRepository.save(f);
    }

    @Override
    public List<Foyer> findAllFoyer() {
        return foyerRepository.findAll();
    }

    @Override
    public Foyer findByIDFoyer(long id) {
        return foyerRepository.findById(id).orElse(Foyer.builder().build());
    }

    @Override
    public void deleteByIDFoyer(long id) {
        foyerRepository.deleteById(id);
    }

    @Override
    public List<Foyer> findFoyerByUniversersite(String nom) {
        return foyerRepository.findFoyerByUniversiteNomUniversite(nom);
    }

    @Override
    public void deleteFoyer(Foyer f) {
        foyerRepository.delete(f);

    }

}
