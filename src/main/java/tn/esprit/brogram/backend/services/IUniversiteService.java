package tn.esprit.brogram.backend.services;

import tn.esprit.brogram.backend.dao.entities.Documents;
import tn.esprit.brogram.backend.dao.entities.Universite;

import java.util.List;


public interface IUniversiteService {
    Universite addUniversite(Universite u);
    List<Universite> addAllUniversite(List<Universite> ls);
    Universite editUniversite(Universite u);
    List<Universite> unifindAll();
    Universite unifindById(long id);
    void unideleteById(long id);
    void unidelete(Universite u);
    Universite updateStatus(long id, String status);
    Universite findUniversiteByEmail(String email);
    List<Universite> getAcceptedUniversites();
    Universite affecterFoyerAUniversite (long idFoyer, String nomUniversite) ;
    Universite desaffecterFoyerAUniversite(long idUniversite);


    Universite findUniversiteByNomUniversiteAndEmail(String name, String email);

    Universite unifindByNomUniv(String nomUniversite);

    List<Documents> downloadDocs(long idUniversite);

    List<Universite> getPendingUniversites();

    void updateStatusIfPendingForMoreThan5Minutes();
}
