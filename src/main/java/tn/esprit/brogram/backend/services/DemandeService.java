package tn.esprit.brogram.backend.services;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.esprit.brogram.backend.dao.entities.*;
import tn.esprit.brogram.backend.dao.repositories.ChamberRepository;
import tn.esprit.brogram.backend.dao.repositories.DemandeRepository;
import tn.esprit.brogram.backend.dao.repositories.ReservationRepository;
import tn.esprit.brogram.backend.dao.repositories.UserRepository;

import java.time.LocalDate;
import java.util.*;

@AllArgsConstructor
@Service
public class DemandeService implements IDemandeService{
    DemandeRepository demandeRepository  ;
    ChamberRepository chamberRepository ;
    UserRepository userRepository ;
    ReservationService reservationService ;
    UserService userService ;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Demande createDemande(Demande demande) {
        demande.setState(StateDemande.ENCOUR);
        demande.setCreatedAt(new Date());
        demande.setDateDemande(LocalDate.now());
        User userE = userService.findByEmail(demande.getEmail());
        if(userE.getId()==0L) {
            User user = new User();
            user.setNomEt(demande.getName());
            user.setPrenomEt(demande.getPrename());
            user.setCin(demande.getCin());
            user.setEcole(demande.getEcole());

            String passwd = Long.toString(demande.getCin());
            user.setEmail(demande.getEmail());
            user.setPassword(passwordEncoder.encode(passwd));
            user.setRole(Roles.ETUDIANT);
            userRepository.save(user);
        }
        return demandeRepository.save(demande);
    }

    ReservationRepository reservationRepository ;

    @Override
    public Demande updateDemande(StateDemande state, int id) {
        Demande demande = demandeRepository.findById(id).orElse(null);

        if (demande == null) {
            return null; // or throw an exception
        }

        demande.setUpdatedAt(new Date());
        User user = userService.findByEmail(demande.getEmail());

        if (hasValidReservation(demande, user)) {
            demande.setState(StateDemande.REFUSER);
            return demandeRepository.save(demande);
        }

        if (state == StateDemande.CONFIRMER) {
            boolean reservationAdded = false;
            List<Chamber> chambers = chamberRepository.findChamberByTypeCAndAndBlocFoyerUniversiteNomUniversite(demande.getTypeChamber(), demande.getEcole());

            for (Chamber chamber : chambers) {
                if (isChamberAvailable(chamber)) {
                    addReservationAndUser(chamber, demande, user);
                    reservationAdded = true;
                    break;
                }
            }

            if (!reservationAdded) {
                demande.setState(StateDemande.REFUSER);
                return demandeRepository.save(demande);
            }
        }

        demande.setState(state);
        return demandeRepository.save(demande);
    }

    private boolean hasValidReservation(Demande demande, User user) {
        List<Reservation> reservationsValidated = reservationRepository.findReservationByEtudiantsemail(user.getEmail());

        for (Reservation reservation : reservationsValidated) {
            boolean b = reservation.getEstValide();
            if (reservation.getAnneeUniversitaire().equals(demande.getAnneeUniversitaire()) && b) {
                return true;
            }
        }

        return false;
    }

    private boolean isChamberAvailable(Chamber chamber) {
        Set<Reservation> reservations = chamber.getRes();
        int validatedReservationCount = (int) reservations.stream().filter(Reservation::getEstValide).count();

        switch (chamber.getTypeC()) {
            case SIMPLE:
                return validatedReservationCount == 0;
            case DOUBLE:
                return validatedReservationCount <= 1;
            case TRIPLE:
                return validatedReservationCount <= 2;
            default:
                return false;
        }
    }

    private void addReservationAndUser(Chamber chamber, Demande demande, User user) {
        if (user.getId() == 0L) {
            User newUser = new User();
            newUser.setNomEt(demande.getName());
            newUser.setPrenomEt(demande.getPrename());
            newUser.setEcole(demande.getEcole());
            newUser.setCin(demande.getCin());
            newUser.setEmail(demande.getEmail());
            newUser.setPassword(passwordEncoder.encode(demande.getEmail()));
            newUser.setRole(Roles.ETUDIANT);
            userRepository.save(newUser);
        }

        List<Long> cin = Collections.singletonList(demande.getCin());
        reservationService.addReservation(chamber.getNumerochamber(), cin, demande.isAutoRenewed());
    }


    @Override
    public List<Demande> listeDemandeByUniversite(String universite) {
        return demandeRepository.findDemandeByEcole(universite);
    }

    @Override
    public List<Demande> listeDemandeByEmail(String email) {
        return demandeRepository.findDemandeByEmail(email);
    }
}
