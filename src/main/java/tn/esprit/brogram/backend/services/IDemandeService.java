package tn.esprit.brogram.backend.services;



import tn.esprit.brogram.backend.dao.entities.Demande;
import tn.esprit.brogram.backend.dao.entities.StateDemande;

import java.util.List;

public interface IDemandeService {
    Demande createDemande(Demande demande);
    Demande updateDemande(StateDemande state , int id);
    List<Demande> listeDemandeByUniversite(String universite);
    List<Demande> listeDemandeByEmail(String email);

}
