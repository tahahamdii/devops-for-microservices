package tn.esprit.brogram.backend.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.brogram.backend.dao.entities.Chamber;
import tn.esprit.brogram.backend.dao.entities.Reservation;
import tn.esprit.brogram.backend.dao.entities.StateReservation;
import tn.esprit.brogram.backend.dao.entities.User;
import tn.esprit.brogram.backend.dao.repositories.ChamberRepository;
import tn.esprit.brogram.backend.dao.repositories.ReservationRepository;
import tn.esprit.brogram.backend.dao.repositories.UserRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
public class ReservationService implements IReservationService {
    ReservationRepository reservationRepository ;
    ChamberRepository chamberRepository ;
    UserRepository userRepository ;
    @Override
    public Set<Reservation> addReservation(long numero , List<Long> cin, boolean autoRenew) {
        Set<Reservation> returnedReservation = new HashSet<>( );
        LocalDate dateDebutAU;
        LocalDate dateFinAU;
        int year = LocalDate.now().getYear() % 100;
        if (LocalDate.now().getMonthValue() <= 7) {
            dateDebutAU = LocalDate.of(Integer.parseInt("20" + (year - 1)), 9, 15);
            dateFinAU = LocalDate.of(Integer.parseInt("20" + year), 6, 30);
        } else {
            dateDebutAU = LocalDate.of(Integer.parseInt("20" + year), 9, 15);
            dateFinAU = LocalDate.of(Integer.parseInt("20" + (year + 1)), 6, 30);
        }
        Chamber c = chamberRepository.findChamberByNumerochamber(numero);
        for(long cinEtudiant:cin){
            User u = userRepository.findEtudiantsByCin(cinEtudiant);
            Reservation r = new Reservation();
            r.setIdReservation(dateDebutAU.getYear()+"-"+dateFinAU.getYear()+"-"+c.getBloc().getNomBloc()
                    +"-"+c.getNumerochamber()+"-"+cinEtudiant);

            r.setAnneeReservation(new Date());
            r.setAnneeUniversitaire(dateDebutAU.getYear()+"-"+dateFinAU.getYear());
            r.setDateDebut(dateDebutAU);
            r.setDateFin(dateFinAU);
            r.setAutoRenew(autoRenew);
            r.setEstValide(true);
            r.setStatus(StateReservation.CONFIRMED);
            c.getRes().add(r);
            chamberRepository.save(c);
            r.getEtudiants().add(u);
            reservationRepository.save(r);
            returnedReservation.add(r);
        }
        return returnedReservation;
    }

    @Override
    public List<Reservation> addAllReservation(List<Reservation> ls) {
        return reservationRepository.saveAll(ls);
    }

    @Override
    public Reservation editReservation(Reservation r) {
        return reservationRepository.save(r);
    }

    @Override
    public Reservation updateReservationState(String id, StateReservation status) {
        Reservation r = reservationRepository.findById(id).orElse(Reservation.builder().build());
        r.setStatus(status);

        return reservationRepository.save(r);
    }

    @Override
    public List<Reservation> findAllReservations() {
        return reservationRepository.findAll();
    }

    @Override
    public List<Reservation> findReservationByEmailEtudiant(String email) {
        return reservationRepository.findReservationByEtudiantsemail(email);
    }

    @Override
    public Reservation findByIdReservation(String id) {
        return reservationRepository.findById(id).orElse(Reservation.builder().build());
    }

    @Override
    public void deleteById(String id) {
        reservationRepository.deleteById(id);
    }

    @Override
    public void deleteReservation(Reservation r) {
        reservationRepository.delete(r);

    }

    @Override
    public void renewAutoReservation() {
        List<Reservation> listeReservation =  reservationRepository.findAll();
        for (Reservation r : listeReservation) {
            if(r.isAutoRenew()){
                LocalDate dateDebutAU;
                LocalDate dateFinAU;
                int year = LocalDate.now().getYear() % 100;
                if (LocalDate.now().getMonthValue() <= 7) {
                    dateDebutAU = LocalDate.of(Integer.parseInt("20" + (year - 1)), 9, 15);
                    dateFinAU = LocalDate.of(Integer.parseInt("20" + year), 6, 30);
                } else {
                    dateDebutAU = LocalDate.of(Integer.parseInt("20" + year), 9, 15);
                    dateFinAU = LocalDate.of(Integer.parseInt("20" + (year + 1)), 6, 30);
                }
                r.setAnneeReservation(new Date());
                r.setAnneeUniversitaire(dateDebutAU.getYear()+"-"+dateFinAU.getYear());
                r.setDateDebut(dateDebutAU);
                r.setDateFin(dateFinAU);
                r.setEstValide(true);
                r.setStatus(StateReservation.CONFIRMED);
                reservationRepository.save(r);
            }
        }
    }
}
