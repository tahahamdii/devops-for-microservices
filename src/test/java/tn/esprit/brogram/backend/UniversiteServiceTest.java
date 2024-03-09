package tn.esprit.brogram.backend;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.brogram.backend.dao.entities.Chamber;
import tn.esprit.brogram.backend.dao.entities.Foyer;
import tn.esprit.brogram.backend.dao.entities.TypeChamber;
import tn.esprit.brogram.backend.dao.entities.Universite;
import tn.esprit.brogram.backend.dao.repositories.FoyerRepository;
import tn.esprit.brogram.backend.services.ChamberService;
import tn.esprit.brogram.backend.services.UniversiteService;

@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class UniversiteServiceTest {

    @Autowired
    private UniversiteService universiteService;
    @Autowired
    private FoyerRepository foyerRepository;
    @Test
    void testAddUniversiteNom(){
        Universite universite  = Universite.builder().nomUniversite("NomUni").build();
        Universite savedUniversite = universiteService.addUniversite(universite);
        Assertions.assertNotNull(savedUniversite.getIdUniversite());

    }
    @Test
    void testAddUniversiteDescription(){
        Universite universite  = Universite.builder().description("Desp Uni").build();
        Universite savedUniversite = universiteService.addUniversite(universite);
        Assertions.assertNotNull(savedUniversite.getIdUniversite());

    }
    @Test
    void testAddUniversiteAdresse(){
        Universite universite  = Universite.builder().adresse("Adresse Uni").build();
        Universite savedUniversite = universiteService.addUniversite(universite);
        Assertions.assertNotNull(savedUniversite.getIdUniversite());

    }
    @Test
    void testAddUniversiteStatus(){
        Universite universite  = Universite.builder().statuts("Pending Uni").build();
        Universite savedUniversite = universiteService.addUniversite(universite);
        Assertions.assertNotNull(savedUniversite.getIdUniversite());

    }
    @Test
    void testAddUniversiteEmail(){
        Universite universite  = Universite.builder().email("Emain Uni").build();
        Universite savedUniversite = universiteService.addUniversite(universite);
        Assertions.assertNotNull(savedUniversite.getIdUniversite());

    }
    @Test
    void testAddUniversiteFirstName(){
        Universite universite  = Universite.builder().firstNameAgent("Agent first Name").build();
        Universite savedUniversite = universiteService.addUniversite(universite);
        Assertions.assertNotNull(savedUniversite.getIdUniversite());

    }
    @Test
    void testAddUniversiteLastName(){
        Universite universite  = Universite.builder().lastNameAgent("Agent last Name").build();
        Universite savedUniversite = universiteService.addUniversite(universite);
        Assertions.assertNotNull(savedUniversite.getIdUniversite());

    }
    @Test
    void testAddUniversiteFoyer() {
        Universite universite = Universite.builder().nomUniversite("NomUni").build();
        Foyer foyer = Foyer.builder().nomFoyer("Foyer Name").build();

        Universite savedUniversite = universiteService.addUniversite(universite);
        foyer.setUniversite(savedUniversite);

        Foyer savedFoyer = foyerRepository.save(foyer);
        savedUniversite.setFoyer(savedFoyer);

        Universite updatedUniversite = universiteService.editUniversite(savedUniversite);

        Assertions.assertNotNull(updatedUniversite.getIdUniversite());
        Assertions.assertNotNull(updatedUniversite.getFoyer());
        Assertions.assertEquals("Foyer Name", updatedUniversite.getFoyer().getNomFoyer());

    }
}
