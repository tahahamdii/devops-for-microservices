package tn.esprit.brogram.backend.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tn.esprit.brogram.backend.dao.entities.Bloc;

import java.util.List;

public interface BlocRepository extends JpaRepository<Bloc,Long> {

    //ByWiWi
    Bloc getBlocByNomBloc(String nomBlog);
    //ByWiWi
    Bloc findBlocByChambers_IdChamber(long idChamber);
    //by wiwi
    boolean existsByNomBloc(String nomBloc);

    List<Bloc> findBlocByFoyer_IdFoyer(long idFoyer);

    @Query("SELECT c.typeC, COUNT(c) FROM Bloc b JOIN b.chambers c WHERE b.idBloc = :blocId GROUP BY c.typeC")
    List<Object[]> countChambersByType(long blocId);



}
