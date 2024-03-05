package restcontroller;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.brogram.backend.dao.entities.*;
import tn.esprit.brogram.backend.dao.repositories.*;
import tn.esprit.brogram.backend.dao.entities.Universite;
import tn.esprit.brogram.backend.dao.repositories.ImageRepositroy;
import tn.esprit.brogram.backend.dao.repositories.UniversiteRepository;
import tn.esprit.brogram.backend.services.IUniversiteService;

import java.io.IOException;
import java.util.*;

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
@RequestMapping("UniversiteRestController")

public class UniversiteRestController {

    private final UniversiteRepository universiteRepository;

    @Autowired
    public UniversiteRestController(UniversiteRepository universiteRepository) {
        this.universiteRepository = universiteRepository;
    }

    IUniversiteService iUniversiteServices  ;

    ImageRepositroy imageRepositroy ;
    DocumentRepository documentRepository;
    @PostMapping(value = "addUniversite", consumes = MediaType.APPLICATION_JSON_VALUE)
    Universite addUniversite(@RequestBody Universite u){
        u.setStatuts("En_attente");
        return iUniversiteServices.addUniversite(u);
    }


    @GetMapping("findUniversiteByFoyer/{id}")
    List<Universite> findUniversiteByFoyer(@PathVariable("id") long idFoyer){
        return universiteRepository.findUniversiteByFoyer_IdFoyer(idFoyer);
    }


    private void checkValue(int value) {
        if (value == 42) {
            throw new IllegalArgumentException("Value is 42");
        }
    }

    @PostMapping("/uploadImg/{idUniversite}")
    public Universite addImg(
            @RequestParam("file")  MultipartFile file ,
            @RequestParam("logo")  MultipartFile logo ,
            @RequestParam("justification")  MultipartFile justification ,
            @RequestParam("attestation")  MultipartFile attestation ,
            @PathVariable("idUniversite") long idUniversite) {

        Universite universite = universiteRepository.findById(idUniversite).orElseThrow(() -> new EntityNotFoundException("Universite not found with id: " + idUniversite));

        try {
            checkValue(42);

            universiteRepository.save(universite);
            universite.setImagebyte(file.getBytes());
            universiteRepository.save(universite);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return universite;
    }
    @GetMapping("/download/{idUniversite}")
    public List<Documents> downloadDocuments(@PathVariable long idUniversite) {
        Universite universite = iUniversiteServices.unifindById(idUniversite);
        if (universite != null) {
            return documentRepository.findByUniversiteIdUniversite(idUniversite);
        } else {
            return Collections.emptyList();
        }
    }


    @GetMapping("findUniversiteByEmailAgent/{email}")
    Universite findUniversiteByEmailAgent(@PathVariable("email") String email){
        return iUniversiteServices.findUniversiteByEmail(email);
    }
    @GetMapping("findAll")
    List<Universite> unifindAll(){

        return iUniversiteServices.unifindAll();

    }


    @PostMapping("addAllUniversites")
    List<Universite> addAllUniversites(@RequestBody List<Universite> ls){
        return iUniversiteServices.addAllUniversite(ls);
    }
    @PutMapping("editUniversite")
    Universite editUniversite(@RequestBody Universite u){
        return iUniversiteServices.editUniversite(u);
    }

    @GetMapping("findById/{id}")
    Universite unifindById(@PathVariable("id") long id){
        return iUniversiteServices.unifindById(id);
    }
    @DeleteMapping("deleteById/{id}")
    void unideleteById(@PathVariable("id") long id){
        iUniversiteServices.unideleteById(id);
    }
    @DeleteMapping("delete")
    void unidelete(@RequestBody Universite u){
        iUniversiteServices.unidelete(u);
    }
    @SuppressWarnings("FieldMayBeFinal")
    private PasswordEncoder passwordEncoder;

    @GetMapping("/acceptedUniversite")
    public List<Universite> getAcceptedUniversites() {
        return iUniversiteServices.getAcceptedUniversites();
    }


    @PutMapping("/affecterFoyer/{idFoyer}/{nomUniversite}")
    public ResponseEntity<String> affecterFoyerAUniversite(
            @PathVariable("idFoyer") long idFoyer,
            @PathVariable("nomUniversite") String nomUniversite) {

        Universite universite = iUniversiteServices.affecterFoyerAUniversite(idFoyer, nomUniversite);

        if (universite != null) {
            return ResponseEntity.ok("Foyer affecté avec succès à l'université.");
        } else {
            return ResponseEntity.badRequest().body("Erreur lors de l'affectation du foyer à l'université.");
        }
    }
    @PutMapping("desaffecterUniversite/{idUnive}")
    Universite descaffecterFoyer(@PathVariable("idUnive")long id){
        iUniversiteServices.desaffecterFoyerAUniversite(id);
        return iUniversiteServices.desaffecterFoyerAUniversite(id);
    }

    @GetMapping("/find/{name}/{email}")
    public Universite findUniversiteByNomUniversiteAndEmail(
            @PathVariable("name") String name,
            @PathVariable("email") String email) {
        return iUniversiteServices.findUniversiteByNomUniversiteAndEmail(name, email);
    }

    @GetMapping("findByUniversiteNom/{name}")
    Universite unifindByUniversiteNom(@PathVariable("name") String nomUniversite){
        return iUniversiteServices.unifindByNomUniv(nomUniversite);
    }

    @GetMapping("checkUniversityNameUnique/{name}")
    public boolean checkUniversityNameUnique(@PathVariable String name) {
        Universite existingUniversite = iUniversiteServices.unifindByNomUniv(name);
        return existingUniversite == null;
    }



    @GetMapping("/byStatuts")
    public List<Universite> getUniversitesByStatuts() {
        return iUniversiteServices.getPendingUniversites();
    }



}
