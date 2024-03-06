package tn.esprit.brogram.backend.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.brogram.backend.dao.entities.Foyer;

import java.util.List;

public interface FoyerRepository extends JpaRepository<Foyer,Long> {
    Foyer findByIdFoyer(Long i);
    List<Foyer> findFoyerByUniversiteNomUniversite(String nom);
    List<Foyer> findFoyerByUniversite_IdUniversite(long idUniversite);
}
