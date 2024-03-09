package tn.esprit.brogram.backend.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.brogram.backend.dao.entities.Reservation;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation,String> {
    List<Reservation> findReservationByEtudiants_email(String email);
    Reservation findByIdReservation(String ss);
}
