package tn.esprit.brogram.backend;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.brogram.backend.dao.entities.*;
import tn.esprit.brogram.backend.dao.repositories.FoyerRepository;
import tn.esprit.brogram.backend.dao.repositories.UniversiteRepository;
import tn.esprit.brogram.backend.services.ChamberService;
import tn.esprit.brogram.backend.services.UniversiteService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
class UniversiteServiceTest {

    @Autowired
    private UniversiteService universiteService;
    @Autowired
    private FoyerRepository foyerRepository;
    @Autowired
    private UniversiteRepository universiteRepository;
    @Test
    void testAddUniversiteNom(){
        Universite universite  = Universite.builder().nomUniversite("NomUni").build();
        Universite savedUniversite = universiteService.addUniversite(universite);
        Assertions.assertNotNull(savedUniversite.getNomUniversite());

    }
    @Test
    void testAddUniversiteDescription(){
        Universite universite  = Universite.builder().description("Desp Uni").build();
        Universite savedUniversite = universiteService.addUniversite(universite);
        Assertions.assertNotNull(savedUniversite.getDescription());

    }

    @Test
    void testAddUniversiteAdresse(){
        Universite universite  = Universite.builder().adresse("Adresse Uni").build();
        Universite savedUniversite = universiteService.addUniversite(universite);
        Assertions.assertNotNull(savedUniversite.getAdresse());
    }

    @Test
    void testAddUniversiteStatus(){
        Universite universite  = Universite.builder().statuts("Pending Uni").build();
        Universite savedUniversite = universiteService.addUniversite(universite);
        Assertions.assertNotNull(savedUniversite.getStatuts());
    }

    @Test
    void testAddUniversiteEmail(){
        Universite universite  = Universite.builder().email("Emain Uni").build();
        Universite savedUniversite = universiteService.addUniversite(universite);
        Assertions.assertNotNull(savedUniversite.getEmail());

    }
    @Test
    void testAddUniversiteFirstName(){
        Universite universite  = Universite.builder().firstNameAgent("Agent first Name").build();
        Universite savedUniversite = universiteService.addUniversite(universite);
        Assertions.assertNotNull(savedUniversite.getFirstNameAgent());

    }
    @Test
    void testAddUniversiteLastName(){
        Universite universite  = Universite.builder().lastNameAgent("Agent last Name").build();
        Universite savedUniversite = universiteService.addUniversite(universite);
        Assertions.assertNotNull(savedUniversite.getLastNameAgent());

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

        Assertions.assertNotNull(updatedUniversite.getNomUniversite());
        Assertions.assertNotNull(updatedUniversite.getFoyer());
        Assertions.assertEquals("Foyer Name", updatedUniversite.getFoyer().getNomFoyer());

    }

    @Test
    void testAddUniversiteCreatedAt() {
        Universite universite = Universite.builder().nomUniversite("NomUni").build();
        universite.setCreatedAt(new Date());

        Universite savedUniversite = universiteService.addUniversite(universite);
        Assertions.assertNotNull(savedUniversite.getNomUniversite());
        Assertions.assertNotNull(savedUniversite.getCreatedAt());

    }

    @Test
    void testAddUniversiteUpdatedAt() {
        Universite universite = Universite.builder().nomUniversite("NomUni").build();
        universite.setUpdatedAt(new Date());

        Universite savedUniversite = universiteService.addUniversite(universite);
        Assertions.assertNotNull(savedUniversite.getNomUniversite());
        Assertions.assertNotNull(savedUniversite.getUpdatedAt());

    }

    @Test
    void testAddAllUniversite() {
        List<Universite> universites = new ArrayList<>();

        Universite universite1 = Universite.builder().nomUniversite("Universite 1").build();
        Universite universite2 = Universite.builder().nomUniversite("Universite 2").build();
        universites.add(universite1);
        universites.add(universite2);

        List<Universite> savedUniversites = universiteService.addAllUniversite(universites);

        Assertions.assertNotNull(savedUniversites);
        Assertions.assertEquals(universites.size(), savedUniversites.size());

        for (Universite savedUniversite : savedUniversites) {
            Assertions.assertNotNull(savedUniversite.getNomUniversite());
        }
    }

    @Test
    void testEditUniversite() {
        Universite universite = Universite.builder().nomUniversite("Universite 1").build();

        Universite savedUniversite = universiteService.editUniversite(universite);

        Assertions.assertNotNull(savedUniversite);
        Assertions.assertEquals(universite.getIdUniversite(), savedUniversite.getIdUniversite());

        Universite updatedUniversite = universiteRepository.findById(savedUniversite.getIdUniversite()).orElse(null);
        Assertions.assertNotNull(updatedUniversite);
        Assertions.assertEquals(universite.getNomUniversite(), updatedUniversite.getNomUniversite());
    }

    @Test
    void testUnifindById() {
        Universite universite = Universite.builder().nomUniversite("Universite 1").build();
        universiteRepository.save(universite);

        Universite foundUniversite = universiteService.unifindById(universite.getIdUniversite());

        Assertions.assertNotNull(foundUniversite);
        Assertions.assertEquals(universite.getIdUniversite(), foundUniversite.getIdUniversite());

        Assertions.assertEquals(universite.getNomUniversite(), foundUniversite.getNomUniversite());
    }
    @Test
    void testUnideleteById() {
        long id = 1L;
        universiteService.unideleteById(id);
        Optional<Universite> deletedUniversite = universiteRepository.findById(id);
        Assertions.assertTrue(deletedUniversite.isEmpty());
    }

    @Test
    void testUnidelete() {
        Universite universite = new Universite();
        universiteService.unidelete(universite);
        Optional<Universite> deletedUniversite = universiteRepository.findById(universite.getIdUniversite());
        Assertions.assertTrue(deletedUniversite.isEmpty());
    }


    @Test
    void testDesaffecterFoyerAUniversite() {
        Universite universite = Universite.builder().nomUniversite("Universite Name").build();
        universite = universiteRepository.save(universite);

        universiteService.desaffecterFoyerAUniversite(universite.getIdUniversite());

        Universite updatedUniversite = universiteRepository.findById(universite.getIdUniversite()).orElse(null);

        Assertions.assertNotNull(updatedUniversite);
        Assertions.assertNull(updatedUniversite.getFoyer());
    }

    @Test
    void testFindAll() {
        Universite universite1 = Universite.builder().nomUniversite("Uni 1").statuts("Pending").description("Description 1").createdAt(new Date()).updatedAt(new Date()).build();
        Universite universite2 = Universite.builder().nomUniversite("Uni 2").statuts("Pending").description("Description 2").createdAt(new Date()).updatedAt(new Date()).build();
        universiteService.addUniversite(universite1);
        universiteService.addUniversite(universite2);
        List<Universite> universites = universiteService.unifindAll();
        Assertions.assertFalse(universites.isEmpty());
    }

}


