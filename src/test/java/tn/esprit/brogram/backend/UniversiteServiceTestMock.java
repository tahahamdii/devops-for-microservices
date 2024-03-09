package tn.esprit.brogram.backend;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.brogram.backend.dao.entities.Chamber;
import tn.esprit.brogram.backend.dao.entities.TypeChamber;
import tn.esprit.brogram.backend.dao.entities.Universite;
import tn.esprit.brogram.backend.dao.repositories.ChamberRepository;
import tn.esprit.brogram.backend.dao.repositories.UniversiteRepository;
import tn.esprit.brogram.backend.services.ChamberService;
import tn.esprit.brogram.backend.services.UniversiteService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class UniversiteServiceTestMock {

    @InjectMocks
    private UniversiteService universiteService ;

    @Mock
    UniversiteRepository universiteRepository;

    @Test
    void testAddUni(){
        Universite universite = Universite.builder().nomUniversite("Uni").build();

        Mockito.when(universiteRepository.save(Mockito.any(Universite.class))).thenReturn(universite);

        Universite savedUniversite = universiteService.addUniversite(universite);
        Assertions.assertNotNull(savedUniversite.getIdUniversite());
        verify(universiteRepository).save(Mockito.any(Universite.class));
    }
    @Test
    void testAddUniDesp(){
        Universite universite = Universite.builder().description("Uni desp").build();

        Mockito.when(universiteRepository.save(Mockito.any(Universite.class))).thenReturn(universite);

        Universite savedUniversite = universiteService.addUniversite(universite);
        Assertions.assertNotNull(savedUniversite.getIdUniversite());
        verify(universiteRepository).save(Mockito.any(Universite.class));
    }
    @Test
    void testAddUniEmail(){
        Universite universite = Universite.builder().email("test@gmail.com").build();

        Mockito.when(universiteRepository.save(Mockito.any(Universite.class))).thenReturn(universite);

        Universite savedUniversite = universiteService.addUniversite(universite);
        Assertions.assertNotNull(savedUniversite.getIdUniversite());
        verify(universiteRepository).save(Mockito.any(Universite.class));
    }

    @Test
    void testAddUniStatus(){
        Universite universite = Universite.builder().statuts("Stat").build();

        Mockito.when(universiteRepository.save(Mockito.any(Universite.class))).thenReturn(universite);

        Universite savedUniversite = universiteService.addUniversite(universite);
        Assertions.assertNotNull(savedUniversite.getIdUniversite());
        verify(universiteRepository).save(Mockito.any(Universite.class));
    }

    @Test
    void testAddUniAdresse(){
        Universite universite = Universite.builder().adresse("Tunis").build();

        Mockito.when(universiteRepository.save(Mockito.any(Universite.class))).thenReturn(universite);

        Universite savedUniversite = universiteService.addUniversite(universite);
        Assertions.assertNotNull(savedUniversite.getIdUniversite());
        verify(universiteRepository).save(Mockito.any(Universite.class));
    }

    @Test
    void testAddUniFirstName(){
        Universite universite = Universite.builder().firstNameAgent("Mao").build();

        Mockito.when(universiteRepository.save(Mockito.any(Universite.class))).thenReturn(universite);

        Universite savedUniversite = universiteService.addUniversite(universite);
        Assertions.assertNotNull(savedUniversite.getIdUniversite());
        verify(universiteRepository).save(Mockito.any(Universite.class));
    }

    @Test
    void testAddUniLastName(){
        Universite universite = Universite.builder().lastNameAgent("Zo").build();

        Mockito.when(universiteRepository.save(Mockito.any(Universite.class))).thenReturn(universite);

        Universite savedUniversite = universiteService.addUniversite(universite);
        Assertions.assertNotNull(savedUniversite.getIdUniversite());
        verify(universiteRepository).save(Mockito.any(Universite.class));
    }

    @Test
    void testAddAllUniversite() {
        List<Universite> universiteList = Arrays.asList(
                Universite.builder().nomUniversite("Uni1").build(),
                Universite.builder().nomUniversite("Uni2").build(),
                Universite.builder().nomUniversite("Uni3").build()
        );
        when(universiteRepository.saveAll(any()
        )).thenReturn(universiteList);

        List<Universite> savedUniversites = universiteService.addAllUniversite(universiteList);
        Assertions.assertEquals(universiteList.size(), savedUniversites.size());
        for (Universite savedUniversite : savedUniversites) {
            Assertions.assertNotNull(savedUniversite.getIdUniversite());
        }
        verify(universiteRepository).saveAll(any());
    }

    @Test
    public void testUnifindAll() {
        List<Universite> universities = universiteService.unifindAll();
        Assertions.assertNotNull(universities);

    }

    @Test
    public void testUnifindById() {
        long id = 1L;
        Universite university = universiteService.unifindById(id);
        Assertions.assertNotNull(university);

    }

    @Test
    public void testUnideleteById() {
        long id = 1L;
        universiteService.unideleteById(id);

    }

    @Test
    public void testUnidelete() {
        Universite university = new Universite();
        universiteService.unidelete(university);

    }
}



