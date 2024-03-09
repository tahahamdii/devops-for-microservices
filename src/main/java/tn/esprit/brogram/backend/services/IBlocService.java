package tn.esprit.brogram.backend.services;

import tn.esprit.brogram.backend.dao.entities.Bloc;

import java.util.List;

public interface IBlocService {
    Bloc addBloc(Bloc b);
    List<Bloc> addAllBlocs(List<Bloc> ls) ;
    Bloc editBloc(Bloc b) ;
    List<Bloc> findAll();
    Bloc findById(long id);
    void deleteByID(long id);
    void delete(Bloc b) ;
    String getBlocNameById(long idBloc);
    Bloc findBlocByChamberIdChamber(long idChamber);
    boolean doesBlocExist(String nomBloc);
    List<Bloc> findBlocByFoyerIdFoyer(long idFoyer);
    double calculateAverageCapacity(long blocId);
    List<Object[]> countChambersByType(long blocId);

}
