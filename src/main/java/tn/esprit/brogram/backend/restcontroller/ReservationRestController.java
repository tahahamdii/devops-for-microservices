package tn.esprit.brogram.backend.restcontroller;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.brogram.backend.dao.entities.Chamber;
import tn.esprit.brogram.backend.dao.entities.Reservation;
import tn.esprit.brogram.backend.dao.entities.StateReservation;
import tn.esprit.brogram.backend.services.IChamberService;
import tn.esprit.brogram.backend.services.IReservationService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
@RequestMapping("ReservationRestController")
public class ReservationRestController {
    @Autowired
    IReservationService iReservationService;
    IChamberService iChamberService;
        @GetMapping("findReservationByUniversiteName/{name}")
        List<Reservation> findReservationByUniversiteName(@PathVariable("name") String name){
        List<Chamber> chambers = iChamberService.findChamberByBlocFoyerUniversiteNomUniversite(name);
        List<Reservation> reservations = new ArrayList<>();
        chambers.forEach(chamber -> reservations.addAll(chamber.getRes()));
        return reservations ;
    }
    @GetMapping("findAllReservation")
    List<Reservation> findAll(){
        return iReservationService.findAllReservations();
    }

    @GetMapping("findReservationByID/{id}")
    Reservation findbyIDReservation(@PathVariable("id") String id){
        return iReservationService.findByIdReservation(id);
    }

    @GetMapping("findReservationByIDEtudiant/{email}")
    List<Reservation> findReservationByIDEtudiant(@PathVariable("email") String email){
        return iReservationService.findReservationByEmailEtudiant(email);
    }

    @PostMapping ("addReservation/{numerochamber}/{autoRenew}")
    Set<Reservation> addReservation( @PathVariable("numerochamber") long numero ,
                               @PathVariable("autoRenew") boolean autoRenew ,
                               @RequestBody  List<Long> cin){
        return iReservationService.addReservation( numero , cin , autoRenew);
    }

    @PostMapping("addAllReservation")
    List<Reservation> addAllReservation(@RequestBody List<Reservation> ls){
        return  iReservationService.addAllReservation(ls);
    }

    @PutMapping("updateReservation")
    Reservation updateReservation(@RequestBody Reservation r){
        return iReservationService.editReservation(r);
    }

    @PutMapping("updateReservationStatus/{id}/{status}")
    Reservation updateReservationStatus(@PathVariable("id") String idReservation , @PathVariable("status") StateReservation status){
        return iReservationService.updateReservationState(idReservation , status);
    }
    @DeleteMapping("DeleteReservation/{id}")
    void deleteReservationByID(@PathVariable("id") String id){
        iReservationService.deleteById(id);
    }

    @DeleteMapping("DeleteReservation")
    void deleteReservation(@RequestBody Reservation r){
        iReservationService.deleteReservation(r);
    }
}
