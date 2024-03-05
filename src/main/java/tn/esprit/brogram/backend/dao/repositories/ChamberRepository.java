package tn.esprit.brogram.backend.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.brogram.backend.dao.entities.Bloc;
import tn.esprit.brogram.backend.dao.entities.Chamber;
import tn.esprit.brogram.backend.dao.entities.TypeChamber;

import java.util.List;

public interface ChamberRepository extends JpaRepository<Chamber,Long> {
  Chamber findChamberByResIdReservation(String id);

  List<Chamber> findByBloc(Bloc b);

  int countChamberByTypeCAndBloc_IdBloc(TypeChamber typeChamber , long idBloc);
  List<Chamber> findChamberByBlocFoyerNomFoyerAndTypeCAndRes_Empty(String nomFoyer , TypeChamber type);


  List<Chamber> findByTypeC(TypeChamber type);

  List<Chamber> findByTypeCAndBlocNomBloc(TypeChamber type, String blocName);
    List<Chamber> findChamberByBlocFoyerUniversiteNomUniversite(String nomUniversite);
    Chamber findChamberByNumerochamber(long numero);

    List<Chamber> findChamberByTypeCAndAndBlocFoyerUniversiteNomUniversite(TypeChamber typeChamber , String nomUniversite);

}
