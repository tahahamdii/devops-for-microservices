package tn.esprit.brogram.backend.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.brogram.backend.dao.entities.Bloc;
import tn.esprit.brogram.backend.dao.entities.Chamber;
import tn.esprit.brogram.backend.dao.entities.Reservation;
import tn.esprit.brogram.backend.dao.entities.TypeChamber;

import tn.esprit.brogram.backend.dao.repositories.BlocRepository;

import tn.esprit.brogram.backend.dao.repositories.ChamberRepository;
import tn.esprit.brogram.backend.dao.repositories.ReservationRepository;

import java.util.*;

import java.util.List;

@AllArgsConstructor
@Service
public class ChamberService implements IChamberService{
    ChamberRepository chamberRepository;
    ReservationRepository reservationRepository ;
    BlocRepository blocRepository;

    @Override
    public Chamber addChamber(Chamber c) {
        return chamberRepository.save(c) ;
    }

    

    @Override
    public Chamber findChamberByResIdReservation(String idReservation) {
        return chamberRepository.findChamberByResIdReservation(idReservation);
    }


    @Override
    public List<Chamber> addAllChambers(List<Chamber> ls) {
        return chamberRepository.saveAll(ls);
    }

    @Override
    public Chamber editChamber(Chamber c) {
        Chamber chamber = chamberRepository.findByIdChamber(c.getIdChamber());
        Bloc b = chamber.getBloc();
        c.setBloc(b);


        return chamberRepository.save(c);

    }

    @Override
    public List<Chamber> findAll() {
        return chamberRepository.findAll();
    }

    @Override
    public Chamber findById(long id) {
        return chamberRepository.findById(id).orElse(Chamber.builder().idChamber(0).numerochamber(0).build());
    }

    @Override
    public void deleteByID(long id) {
        chamberRepository.deleteById(id);

    }

    @Override
    public void delete(Chamber c) {
        chamberRepository.delete(c);
    }

    @Override
    public List<Chamber> getChambresParNomBloc(String nomBloc) {
        Bloc b = blocRepository.getBlocByNomBloc(nomBloc);
        return chamberRepository.findByBloc(b) ;
    }
    @Override
    public long nbChambreParTypeEtBloc(TypeChamber type, long idBloc) {
        return chamberRepository.countChamberByTypeCAndBlocIdBloc(type , idBloc);
    }
    @Override
    public List<Chamber> getChambresNonReserveParNomFoyerEtTypeChambre(String nomFoyer, TypeChamber type) {
        return chamberRepository.findChamberByBlocFoyerNomFoyerAndTypeCAndResEmpty(nomFoyer,type);
    }

    @Override
    public List<Chamber> getChambersByType(TypeChamber type) {
        return chamberRepository.findByTypeC(type);
    }

    @Override
    public List<Chamber> getChambersByTypeAndBlocName(TypeChamber type, String blocName) {
        return chamberRepository.findByTypeCAndBlocNomBloc(type, blocName);
    }

    @Override
    public List<Chamber> findChamberByBlocFoyerUniversiteNomUniversite(String nomUniversite) {

        return chamberRepository.findChamberByBlocFoyerUniversiteNomUniversite(nomUniversite);
    }
    @Override
    public List<Chamber> findAvailableChamberByBlocFoyerUniversiteNomUniversite(String nomUniversite) {
        List<Chamber> chambers = chamberRepository.findChamberByBlocFoyerUniversiteNomUniversite(nomUniversite);
        List<Chamber> finalChambers = new ArrayList<>();

        for (Chamber c : chambers) {
            boolean test = false;
            Set<Reservation> reservations = c.getRes();
            int validatedReservationCount = (int) reservations.stream().filter(Reservation::getEstValide).count();

            switch (c.getTypeC()) {
                case SIMPLE:
                    test = validatedReservationCount == 0;
                    break;
                case DOUBLE:
                    test = validatedReservationCount <= 1;
                    break;
                case TRIPLE:
                    test = validatedReservationCount <= 2;
                    break;
            }

            if (test) {
                finalChambers.add(c);
            }
        }

        return finalChambers;
    }

    @Override
    public Chamber addChamberReservation(long idCh , Reservation r) {
        Chamber ch = chamberRepository.findById(idCh).orElse(Chamber.builder().build());
        ch.getRes().add(r);
        return chamberRepository.save(ch);
    }


    @Override
    public void affecterBlocAChambre(long idChamber, long idBloc) {
        Chamber chambre = chamberRepository.findById(idChamber).orElseThrow(() -> new EntityNotFoundException("Chambre not found"));
        Bloc bloc = blocRepository.findById(idBloc).orElseThrow(() -> new EntityNotFoundException("Bloc not found"));

        chambre.setBloc(bloc);
        chamberRepository.save(chambre);
    }




}
