//package tn.esprit.brogram.backend.Services;
//import jakarta.persistence.EntityNotFoundException;
//import lombok.AllArgsConstructor;
//import org.springframework.stereotype.Service;
//import tn.esprit.brogram.backend.DAO.Entities.Bloc;
//import tn.esprit.brogram.backend.DAO.Entities.Chamber;
//import tn.esprit.brogram.backend.DAO.Entities.Reservation;
//import tn.esprit.brogram.backend.DAO.Entities.TypeChamber;
//
//import tn.esprit.brogram.backend.DAO.Repositories.BlocRepository;
//
//import tn.esprit.brogram.backend.DAO.Repositories.ChamberRepository;
//import tn.esprit.brogram.backend.DAO.Repositories.ReservationRepository;
//
//import java.util.*;
//
//import java.util.List;
//
//@AllArgsConstructor
//@Service
//public class ChamberService implements IChamberService{
//    ChamberRepository chamberRepository;
//    ReservationRepository reservationRepository ;
//    BlocRepository blocRepository;
//    @Override
//    public Chamber addChamber(Chamber c) {
//        return chamberRepository.save(c) ;
//    }
//
//    @Override
//    public Chamber addChamberReservation(long idCh , Reservation r) {
//        Chamber ch = chamberRepository.findById(idCh).orElse(Chamber.builder().build());
//        ch.getRes().add(r);
//
//        return chamberRepository.save(ch);
//    }
//
//    @Override
//    public Chamber findChamberByResIdReservation(String idReservation) {
//        return chamberRepository.findChamberByResIdReservation(idReservation);
//    }
//
//
//    @Override
//    public List<Chamber> addAllChambers(List<Chamber> ls) {
//        return chamberRepository.saveAll(ls);
//    }
//
//    @Override
//    public Chamber editChamber(Chamber c) {
//        Chamber chamber = chamberRepository.findById(c.getIdChamber()).get();
//        Bloc b = chamber.getBloc();
//        c.setBloc(b);
//
//
//        return chamberRepository.save(c);
//
//    }
//
//    @Override
//    public List<Chamber> findAll() {
//        return chamberRepository.findAll();
//    }
//
//    @Override
//    public Chamber findById(long id) {
//        Chamber c = chamberRepository.findById(id).orElse(Chamber.builder().idChamber(0).numerochamber(0).build());
//        if(c.getBloc() == null){
//        }
//        return chamberRepository.findById(id).orElse(Chamber.builder().idChamber(0).numerochamber(0).build());
//    }
//
//    @Override
//    public void deleteByID(long id) {
//        chamberRepository.deleteById(id);
//
//    }
//
//    @Override
//    public void delete(Chamber c) {
//        chamberRepository.delete(c);
//
//    }
//
//    @Override
//    public List<Chamber> getChambresParNomBloc(String nomBloc) {
//        Bloc b = blocRepository.getBlocByNomBloc(nomBloc);
//        return chamberRepository.findByBloc(b) ;
//    }
//    @Override
//    public long nbChambreParTypeEtBloc(TypeChamber type, long idBloc) {
//        int c = chamberRepository.countChamberByTypeCAndBloc_IdBloc(type , idBloc);
//        return c;
//    }
//    @Override
//    public List<Chamber> getChambresNonReserveParNomFoyerEtTypeChambre(String nomFoyer, TypeChamber type) {
//        return chamberRepository.findChamberByBlocFoyerNomFoyerAndTypeCAndRes_Empty(nomFoyer,type);
//    }
//
//    @Override
//    public List<Chamber> getChambersByType(TypeChamber type) {
//        return chamberRepository.findByTypeC(type);
//    }
//
//    @Override
//    public List<Chamber> getChambersByTypeAndBlocName(TypeChamber type, String blocName) {
//        return chamberRepository.findByTypeCAndBlocNomBloc(type, blocName);
//    }
//
//
//
//    @Override
//    public List<Chamber> findChamberByBlocFoyerUniversiteNomUniversite(String nomUniversite) {
//
//        return chamberRepository.findChamberByBlocFoyerUniversiteNomUniversite(nomUniversite);
//    }
//
//    @Override
//    public List<Chamber> findAvailableChamberByBlocFoyerUniversiteNomUniversite(String nomUniversite) {
//        List<Chamber> chambers  =chamberRepository.findChamberByBlocFoyerUniversiteNomUniversite(nomUniversite);
//        List<Chamber> finalChambers  = new ArrayList<>( );
//        for (Chamber c :chambers){
//            boolean test = false ;
//            Set<Reservation> reservations = c.getRes();
//            int i = (int) reservations.stream().filter(Reservation::getEstValide).count();
//            test = switch (c.getTypeC()) {
//                case SIMPLE -> i == 0;
//                case DOUBLE -> i <= 1;
//                case TRIPLE -> i <= 2;
//            };
//            if(test){
//                finalChambers.add(c);
//            }
//        }
//        return finalChambers;
//    }
//
//    @Override
//    public void affecterBlocAChambre(long idChamber, long idBloc) {
//        Chamber chambre = chamberRepository.findById(idChamber).orElseThrow(() -> new EntityNotFoundException("Chambre not found"));
//        Bloc bloc = blocRepository.findById(idBloc).orElseThrow(() -> new EntityNotFoundException("Bloc not found"));
//
//        chambre.setBloc(bloc);
//        chamberRepository.save(chambre);
//    }
//}
