package tn.esprit.brogram.backend.restcontroller;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.brogram.backend.dao.entities.Chamber;
import tn.esprit.brogram.backend.dao.entities.Reservation;
import tn.esprit.brogram.backend.dao.entities.TypeChamber;
import tn.esprit.brogram.backend.dao.errors.CustomException;
import tn.esprit.brogram.backend.dao.repositories.ChamberRepository;
import tn.esprit.brogram.backend.dao.repositories.ImageRepository;
import tn.esprit.brogram.backend.services.IChamberService;


import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@AllArgsConstructor
@RequestMapping("ChamberRestController")
public class ChamberRestController {
    @Autowired
    IChamberService iChamberService ;
    @Autowired
    ChamberRepository chamberRepo;
    ImageRepository imageRepositroy ;

    @GetMapping("findChambersbyUniversite/{nom}")
    public List<Chamber> findChambersbyUniversite(@PathVariable("nom") String nom){
        return iChamberService.findChamberByBlocFoyerUniversiteNomUniversite(nom);
    }
    @GetMapping("findAvailableChambersbyUniversite/{nom}")
    public List<Chamber> findAvailableChambersbyUniversite(@PathVariable("nom") String nom){
        return iChamberService.findAvailableChamberByBlocFoyerUniversiteNomUniversite(nom);
    }
    @GetMapping("findAllChambers")
    public List<Chamber> findAll(){
        return iChamberService.findAll();
    }

    @GetMapping("findChamberByID/{id}")
    public Chamber findChamberByID(@PathVariable("id") long id){
        return iChamberService.findById(id);
    }
    @PostMapping("addChamber")
    public Chamber addChamber(@RequestBody Chamber c){
        c.setCreatedAt(new Date());
        c.setEtat(true);
        return iChamberService.addChamber(c);

    }

    @PutMapping("putChamberReservation/{id}")
    public Chamber putChamberReservation(@PathVariable("id") long idCh , @RequestBody Reservation r){
        return iChamberService.addChamberReservation(idCh , r);
    }

    @GetMapping("findChamberByReservationID/{id}")
    public Chamber getChamberByReservation(@PathVariable("id") String idReservation){
        return iChamberService.findChamberByResIdReservation(idReservation) ;
    }

    @PostMapping("/addAllChambers")
    public List<Chamber> addAllChambers(@RequestBody List<Chamber> ls){
        return iChamberService.addAllChambers(ls);
    }
    @PutMapping("updateChamber")
    public Chamber editChamber(@RequestBody Chamber c){
        c.setUpdatedAt(new Date());
        return iChamberService.editChamber(c);
    }

    @DeleteMapping("deleteChamberById/{id}")
    public void deleteChamberByID(@PathVariable("id") long id){

        iChamberService.deleteByID(id);
    }

    @DeleteMapping("deleteChamber")
    public void deleteChmber(@RequestBody Chamber c){
        iChamberService.delete(c);
    }
    @GetMapping("getChamberList/{nomBloc}")
    public List<Chamber> getChambresParNomBloc(@PathVariable("nomBloc") String nomBloc){
        return iChamberService.getChambresParNomBloc(nomBloc);
    }
    @GetMapping("nbChambreParTypeEtBloc/{type}/{idBloc}")
    public long nbChambreParTypeEtBloc(@PathVariable("type") TypeChamber type , @PathVariable("idBloc") long idBloc){
        return iChamberService.nbChambreParTypeEtBloc(type , idBloc);
    }
    @GetMapping("chamberListNonReserver/{type}/{nomFoyer}")
    public List<Chamber> getChambresNonReserveParNomFoyerEtTypeChambre(@PathVariable("type") TypeChamber type, @PathVariable("nomFoyer") String nomFoyer) {
        return iChamberService.getChambresNonReserveParNomFoyerEtTypeChambre(nomFoyer, type);
    }
    @PostMapping("uploadImg/{idChamber}")
    public Chamber addImg(@RequestParam("file") MultipartFile file , @PathVariable("idChamber") long idChamber) {

        Chamber chamber = chamberRepo.findByIdChamber(idChamber);


        try {
            chamber.setImagebyte(file.getBytes());
            chamberRepo.save(chamber);
        } catch (IOException e) {

            throw new CustomException(e.getMessage());
        }
        return chamber;
    }

    @GetMapping("/getChambersByType/{type}")
    public List<Chamber> getChambersByType(@PathVariable TypeChamber type) {
        return iChamberService.getChambersByType(type);
    }


    @GetMapping("/byTypeAndBloc")
    public ResponseEntity<List<Chamber>> getChambersByTypeAndBloc(
            @RequestParam("type") TypeChamber type,
            @RequestParam("blocName") String blocName) {
        List<Chamber> chambers = iChamberService.getChambersByTypeAndBlocName(type, blocName);
        return ResponseEntity.ok(chambers);
    }

    @PostMapping("/affecterBlocAChambre")
    public ResponseEntity<Void> affecterBlocAChambre(@RequestParam long idChamber, @RequestParam long idBloc) {
        iChamberService.affecterBlocAChambre(idChamber, idBloc);
        return ResponseEntity.ok().build();
    }
}
