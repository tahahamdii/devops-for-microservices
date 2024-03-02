package tn.esprit.brogram.backend.RestController;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.brogram.backend.DAO.Entities.*;
import tn.esprit.brogram.backend.DAO.Repositories.*;
import tn.esprit.brogram.backend.DAO.Entities.Bloc;
import tn.esprit.brogram.backend.DAO.Entities.Universite;
import tn.esprit.brogram.backend.DAO.Repositories.ImageRepositroy;
import tn.esprit.brogram.backend.DAO.Repositories.UniversiteRepository;

import tn.esprit.brogram.backend.Services.EmailService;
import tn.esprit.brogram.backend.Services.IUniversiteService;

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
        Universite universite = iUniversiteServices.UnifindById(idUniversite);
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
    List<Universite> UnifindAll(){

        return iUniversiteServices.UnifindAll();

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
    Universite UnifindById(@PathVariable("id") long id){
        return iUniversiteServices.UnifindById(id);
    }
    @DeleteMapping("deleteById/{id}")
    void UnideleteById(@PathVariable("id") long id){
        iUniversiteServices.UnideleteById(id);
    }
    @DeleteMapping("delete")
    void Unidelete(@RequestBody Universite u){
        iUniversiteServices.Unidelete(u);
    }
    @SuppressWarnings("FieldMayBeFinal")
    private PasswordEncoder passwordEncoder;
    UserRepository userRepository ;
    EmailService emailService ;
    @PutMapping("updateStatus/{id}")
    public ResponseEntity<Universite> updateStatus(@PathVariable long id, @RequestParam String status) {
        try {
            Universite updatedUniversite = iUniversiteServices.updateStatus(id, status);
            if(status.equals("Accepté")){
                User user = new User();
                user.setNomEt(updatedUniversite.getFirstNameAgent());
                user.setPrenomEt(updatedUniversite.getLastNameAgent());
                user.setCin(12345678);
                user.setEcole(updatedUniversite.getNomUniversite());


                user.setEmail(updatedUniversite.getEmail());
                user.setPassword(passwordEncoder.encode((updatedUniversite.getEmail())));
                user.setRole(Roles.AGENTUNIVERSITE);
                emailService.sendSimpleMail(new EmailDetails(updatedUniversite.getEmail() ,
                        "YOUR UNIVERSITE IS BEEN ACCEPTED SUCCESSFYLLY TO LOGIN PRESS <a href='localhost:4200/login'>here </a>" , "subject" ,null));
                userRepository.save(user);
            }else{
                emailService.sendSimpleMail(new EmailDetails(updatedUniversite.getEmail() ,
                        "YOUR UNIVERSITE IS BEEN REFUSSED CHECK YOUR PROVIDED DATA " , "subject" ,null));

            }
            return new ResponseEntity<>(updatedUniversite, HttpStatus.OK);
        } catch (Exception ex) {
            // Handle any exceptions here
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
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
    Universite UnifindByUniversiteNom(@PathVariable("name") String nomUniversite){
        return iUniversiteServices.UnifindByNomUniv(nomUniversite);
    }

    @GetMapping("checkUniversityNameUnique/{name}")
    public boolean checkUniversityNameUnique(@PathVariable String name) {
        Universite existingUniversite = iUniversiteServices.UnifindByNomUniv(name);
        return existingUniversite == null;
    }



    @GetMapping("/byStatuts")
    public List<Universite> getUniversitesByStatuts() {
        return iUniversiteServices.getPendingUniversites();
    }



}
