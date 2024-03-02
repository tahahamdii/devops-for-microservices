package tn.esprit.brogram.backend.Services;

import tn.esprit.brogram.backend.DAO.Entities.Documents;
import tn.esprit.brogram.backend.DAO.Entities.Universite;

import java.util.List;


public interface IUniversiteService {
    Universite addUniversite(Universite u);
    List<Universite> addAllUniversite(List<Universite> ls);
    Universite editUniversite(Universite u);
    List<Universite> UnifindAll();
    Universite UnifindById(long id);
    void UnideleteById(long id);
    void Unidelete(Universite u);
    Universite updateStatus(long id, String status);
    Universite findUniversiteByEmail(String email);
    List<Universite> getAcceptedUniversites();



    Universite affecterFoyerAUniversite (long idFoyer, String nomUniversite) ;
    Universite desaffecterFoyerAUniversite(long idUniversite);


    Universite findUniversiteByNomUniversiteAndEmail(String name, String email);

    Universite UnifindByNomUniv(String nomUniversite);

    List<Documents> downloadDocs(long idUniversite);

    List<Universite> getPendingUniversites();

    void updateStatusIfPendingForMoreThan5Minutes();
}
