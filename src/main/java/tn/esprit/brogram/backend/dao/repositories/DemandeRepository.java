package tn.esprit.brogram.backend.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.brogram.backend.dao.entities.Demande;


import java.util.List;

public interface DemandeRepository extends JpaRepository<Demande,Integer> {
    List<Demande> findDemandeByEcole(String ecole);

    List<Demande> findDemandeByEmail(String email);
}
