package tn.esprit.brogram.backend.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.brogram.backend.dao.entities.Bloc;
import tn.esprit.brogram.backend.dao.entities.Chamber;
import tn.esprit.brogram.backend.dao.entities.TypeChamber;

import java.util.List;

public interface ChamberRepository extends JpaRepository<Chamber,Long> {
  Chamber findChamberByResIdReservation(String id);

  Chamber findByIdChamber(Long id);

  List<Chamber> findByBloc(Bloc b);

  int countChamberByTypeCAndBlocIdBloc(TypeChamber typeChamber , long idBloc);
  List<Chamber> findChamberByBlocFoyerNomFoyerAndTypeCAndResEmpty(String nomFoyer , TypeChamber type);


  List<Chamber> findByTypeC(TypeChamber type);

  List<Chamber> findByTypeCAndBlocNomBloc(TypeChamber type, String blocName);
    List<Chamber> findChamberByBlocFoyerUniversiteNomUniversite(String nomUniversite);
    Chamber findChamberByNumerochamber(long numero);

    List<Chamber> findChamberByTypeCAndAndBlocFoyerUniversiteNomUniversite(TypeChamber typeChamber , String nomUniversite);

}
