package tn.esprit.brogram.backend.restcontroller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.brogram.backend.dao.entities.Bloc;
import tn.esprit.brogram.backend.dao.entities.Foyer;
import tn.esprit.brogram.backend.dao.entities.Universite;
import tn.esprit.brogram.backend.dao.errors.CustomException;
import tn.esprit.brogram.backend.dao.repositories.BlocRepository;
import tn.esprit.brogram.backend.dao.repositories.FoyerRepository;
import tn.esprit.brogram.backend.dao.repositories.ImageRepositroy;
import tn.esprit.brogram.backend.dao.repositories.UniversiteRepository;
import tn.esprit.brogram.backend.services.IFoyerService;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
@RequestMapping("FoyerRestController")
public class FoyerRestController {
    private final FoyerRepository foyerRepository;

    @Autowired
    public FoyerRestController(FoyerRepository foyerRepository) {
        this.foyerRepository = foyerRepository;
    }

    @Autowired
    IFoyerService iFoyerService;
    BlocRepository blocRepository;
    private UniversiteRepository universiteRepository;

    @GetMapping("findAllFoyer")
    public List<Foyer> findAll() {
        return iFoyerService.findAllFoyer();
    }

    @GetMapping("findFoyerByUnversiteName/{nom}")
    public List<Foyer> findFoyerByUniversiteNomUniversite(@PathVariable("nom") String nom) {
        return iFoyerService.findFoyerByUniversersite(nom);
    }

    @GetMapping("findByIdFoyer/{id}")
    public Foyer findbyIdFoyer(@PathVariable("id") long id) {
        return iFoyerService.findByIDFoyer(id);
    }


    @PostMapping("AddFoyer/{name}")
    public Foyer addFoyer(@RequestBody Foyer f, @PathVariable("name") String name) {

        return iFoyerService.addFoyer(f, name);
    }

    @PostMapping("AddAllFoyer")
    public List<Foyer> addAllFoyer(@RequestBody List<Foyer> ls) {
        return iFoyerService.addAllFoyer(ls);
    }

    @PutMapping("UpdateFoyer")
    public Foyer updateFoyer(@RequestBody Foyer f) {
        f.setUpdatedAt(new Date());
        return iFoyerService.editFoyer(f);
    }

    @DeleteMapping("DeleteFoyerByID/{id}")
    public void deleteFoyerByID(@PathVariable("id") long id) {
        iFoyerService.deleteByIDFoyer(id);
    }

    @DeleteMapping("DeleteFoyer")
    public void deleteFoyer(@RequestBody Foyer f) {
        iFoyerService.deleteFoyer(f);
    }

    @GetMapping("findFoyerByUniversite/{id}")
    public List<Foyer> findFoyerByUniversite(@PathVariable("id") long idUniversite) {
        return foyerRepository.findFoyerByUniversiteIdUniversite(idUniversite);
    }

    @PutMapping("updateEtatById/{idFoyer}")
    public ResponseEntity<String> updateFoyerEtatById(@PathVariable long idFoyer) {
        Foyer foyer = foyerRepository.findById(idFoyer).orElse(null);
        if (foyer != null) {
            foyer.setEtat(true);
            Universite universite = foyer.getUniversite();
            if (universite != null) {
                universite.setFoyer(null);
            }
            List<Bloc> blocs = foyer.getBlocs();
            if (blocs != null) {
                for (Bloc bloc : blocs) {
                    bloc.setFoyer(null);
                }
            }
            foyer.setUniversite(null);
            foyer.setBlocs(null);
            foyerRepository.save(foyer);
            return ResponseEntity.ok("Foyer Etat updated successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    ImageRepositroy imageRepositroy;

    @PostMapping("/uploadImg/{idFoyer}")
    public Foyer addImg(@RequestParam("file") MultipartFile file, @PathVariable("idFoyer") long idFoyer) {

        Foyer foyer = foyerRepository.findByIdFoyer(idFoyer);
        try {
            foyer.setImagebyte(file.getBytes());
            foyerRepository.save(foyer);
        } catch (IOException e) {
            throw new CustomException(e.getMessage());
        }
        return foyer;

    }
}
