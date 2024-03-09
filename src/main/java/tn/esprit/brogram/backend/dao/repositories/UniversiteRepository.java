package tn.esprit.brogram.backend.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.esprit.brogram.backend.dao.entities.Universite;

import java.util.List;

public interface UniversiteRepository extends JpaRepository<Universite,Long> {
    Universite findUnBynomUniversite(String name);

    Universite findUniversiteByEmail(String email);


    List<Universite> findByStatuts(String statuts);


    Universite findUniversiteByNomUniversiteAndEmail(String name, String email);


    List<Universite> findUniversiteByFoyer_IdFoyer(long idFoyer);
    Universite findUniversiteByFoyerIdFoyer(long idFoyer);

}
