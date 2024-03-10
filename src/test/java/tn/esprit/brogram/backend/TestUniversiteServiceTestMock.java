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
import tn.esprit.brogram.backend.dao.entities.Universite;
import tn.esprit.brogram.backend.dao.repositories.UniversiteRepository;
import tn.esprit.brogram.backend.services.UniversiteService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.test.context.TestPropertySource;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
class UniversiteServiceTestMock {

    @InjectMocks
    private UniversiteService universiteService ;

    @Mock
    UniversiteRepository universiteRepository;

    @Test
    void testAddUni(){
        Universite universite = Universite.builder().nomUniversite("Uni").build();

        Mockito.when(universiteRepository.save(Mockito.any(Universite.class))).thenReturn(universite);

        Universite savedUniversite = universiteService.addUniversite(universite);
        Assertions.assertNotNull(savedUniversite.getNomUniversite());
        verify(universiteRepository).save(Mockito.any(Universite.class));
    }
    @Test
    void testAddUniDesp(){
        Universite universite = Universite.builder().description("Uni desp").build();

        Mockito.when(universiteRepository.save(Mockito.any(Universite.class))).thenReturn(universite);

        Universite savedUniversite = universiteService.addUniversite(universite);
        Assertions.assertNotNull(savedUniversite.getNomUniversite());
        verify(universiteRepository).save(Mockito.any(Universite.class));
    }
    @Test
    void testAddUniEmail(){
        Universite universite = Universite.builder().email("test@gmail.com").build();

        Mockito.when(universiteRepository.save(Mockito.any(Universite.class))).thenReturn(universite);

        Universite savedUniversite = universiteService.addUniversite(universite);
        Assertions.assertNotNull(savedUniversite.getNomUniversite());
        verify(universiteRepository).save(Mockito.any(Universite.class));
    }

    @Test
    void testAddUniStatus(){
        Universite universite = Universite.builder().statuts("Stat").build();

        Mockito.when(universiteRepository.save(Mockito.any(Universite.class))).thenReturn(universite);

        Universite savedUniversite = universiteService.addUniversite(universite);
        Assertions.assertNotNull(savedUniversite.getNomUniversite());
        verify(universiteRepository).save(Mockito.any(Universite.class));
    }

    @Test
    void testAddUniAdresse(){
        Universite universite = Universite.builder().adresse("Tunis").build();

        Mockito.when(universiteRepository.save(Mockito.any(Universite.class))).thenReturn(universite);

        Universite savedUniversite = universiteService.addUniversite(universite);
        Assertions.assertNotNull(savedUniversite.getNomUniversite());
        verify(universiteRepository).save(Mockito.any(Universite.class));
    }

    @Test
    void testAddUniFirstName(){
        Universite universite = Universite.builder().firstNameAgent("Mao").build();

        Mockito.when(universiteRepository.save(Mockito.any(Universite.class))).thenReturn(universite);

        Universite savedUniversite = universiteService.addUniversite(universite);
        Assertions.assertNotNull(savedUniversite.getNomUniversite());
        verify(universiteRepository).save(Mockito.any(Universite.class));
    }

    @Test
    void testAddUniLastName(){
        Universite universite = Universite.builder().lastNameAgent("Zo").build();

        Mockito.when(universiteRepository.save(Mockito.any(Universite.class))).thenReturn(universite);

        Universite savedUniversite = universiteService.addUniversite(universite);
        Assertions.assertNotNull(savedUniversite.getNomUniversite());
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
            Assertions.assertNotNull(savedUniversite.getNomUniversite());
        }
        verify(universiteRepository).saveAll(any());
    }

    @Test
     void testUnifindAll() {
        List<Universite> universities = universiteService.unifindAll();
        Assertions.assertNotNull(universities);

    }

    @Test
     void testUnifindById() {
        long id = 1L;
        Universite university = universiteService.unifindById(id);
        Assertions.assertNotNull(university);

    }
    

    @Test
    void deleteUni() {
        Universite universite = Universite.builder().nomUniversite("Uni a").statuts("Pending").description("this is uni").build();
        universiteService.unidelete(universite);
        Mockito.verify(universiteRepository).delete(universite);
    }




        @Test
        void testFindById() {
            long id = 1;
            Universite expectedUniversite = Universite.builder().idUniversite(id).nomUniversite("A").description("Description 1").statuts("true").build();

            when(universiteRepository.findById(id)).thenReturn(Optional.of(expectedUniversite));

            Universite actualUniversite = universiteService.unifindById(id);

            assertNotNull(actualUniversite);
            assertEquals(expectedUniversite.getIdUniversite(), actualUniversite.getIdUniversite());
            assertEquals(expectedUniversite.getNomUniversite(), actualUniversite.getNomUniversite());
            assertEquals(expectedUniversite.getDescription(), actualUniversite.getDescription());
            assertEquals(expectedUniversite.getStatuts(), actualUniversite.getStatuts());

            verify(universiteRepository).findById(id);
        }

        @Test
        void testDeleteByID() {
            long idToDelete = 1;

            universiteService.unideleteById(idToDelete);

            verify(universiteRepository).deleteById(idToDelete);
        }

    }



