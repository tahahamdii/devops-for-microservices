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
import org.springframework.test.context.TestPropertySource;
import tn.esprit.brogram.backend.dao.entities.Bloc;
import tn.esprit.brogram.backend.dao.entities.Chamber;
import tn.esprit.brogram.backend.dao.entities.Reservation;
import tn.esprit.brogram.backend.dao.entities.TypeChamber;
import tn.esprit.brogram.backend.dao.repositories.BlocRepository;
import tn.esprit.brogram.backend.dao.repositories.ChamberRepository;
import tn.esprit.brogram.backend.dao.repositories.ReservationRepository;
import tn.esprit.brogram.backend.services.ChamberService;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
class TestChamberServiceTestMock {

    @InjectMocks
    private ChamberService chamberService;

    @Mock
    ChamberRepository chamberRepository;

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private BlocRepository blocRepository ;


    @Test
    void testAddChambre(){
        Chamber chamber = Chamber.builder().numerochamber(77).description("anyDescription").etat(true).typeC(TypeChamber.SIMPLE).build();

        when(chamberRepository.save(any(Chamber.class))).thenReturn(chamber);

        Chamber savedChamber = chamberService.addChamber(chamber);
        assertNotNull(savedChamber.getIdChamber());
        assertNotNull(savedChamber.getDescription());
        verify(chamberRepository).save(any(Chamber.class));


    }

    @Test
    void testFindChamberByResIdReservation() {
        Chamber chamber = Chamber.builder()
                .idChamber(30)
                .numerochamber(77)
                .description("anyDescription")
                .etat(true)
                .typeC(TypeChamber.SIMPLE)
                .res(new HashSet<>())
                .build();

        Reservation reservation = new Reservation();
        reservation.setIdReservation("yy");

        chamber.getRes().add(reservation);

        when(chamberRepository.findChamberByResIdReservation("yy")).thenReturn(chamber);

        Chamber foundChamber = chamberService.findChamberByResIdReservation("yy");

        assertNotNull(foundChamber);
        assertEquals(chamber.getIdChamber(), foundChamber.getIdChamber());
        assertEquals(chamber.getNumerochamber(), foundChamber.getNumerochamber());
        assertEquals(chamber.getDescription(), foundChamber.getDescription());
        assertEquals(chamber.isEtat(), foundChamber.isEtat());
        assertEquals(chamber.getTypeC(), foundChamber.getTypeC());

        verify(chamberRepository).findChamberByResIdReservation("yy");
    }



    @Test
    void testAddAllChambers() {
        List<Chamber> chambersToAdd = new ArrayList<>();
        Chamber chamber1 = Chamber.builder().numerochamber(1).description("Chamber 1").etat(true).build();
        Chamber chamber2 = Chamber.builder().numerochamber(2).description("Chamber 2").etat(false).build();
        chambersToAdd.add(chamber1);
        chambersToAdd.add(chamber2);

        when(chamberRepository.saveAll(chambersToAdd)).thenReturn(chambersToAdd);

        List<Chamber> savedChambers = chamberService.addAllChambers(chambersToAdd);

        assertNotNull(savedChambers);
        assertEquals(2, savedChambers.size());
        assertEquals(chamber1, savedChambers.get(0));
        assertEquals(chamber2, savedChambers.get(1));

        verify(chamberRepository).saveAll(chambersToAdd);
    }

    @Test
    void testEditChamber() {
        Chamber chamberToUpdate = Chamber.builder().idChamber(1).numerochamber(101).description("Updated description").etat(false).build();
        Bloc bloc = new Bloc();
        chamberToUpdate.setBloc(bloc);

        when(chamberRepository.findByIdChamber(1L)).thenReturn(chamberToUpdate); // Stubbing to return the chamber to update
        when(chamberRepository.save(any(Chamber.class))).thenAnswer(invocation -> invocation.getArgument(0)); // Stubbing to return the saved chamber

        Chamber updatedChamber = chamberService.editChamber(chamberToUpdate);

        assertNotNull(updatedChamber);
        assertEquals(chamberToUpdate.getIdChamber(), updatedChamber.getIdChamber());
        assertEquals(chamberToUpdate.getNumerochamber(), updatedChamber.getNumerochamber());
        assertEquals(chamberToUpdate.getDescription(), updatedChamber.getDescription());
        assertEquals(chamberToUpdate.isEtat(), updatedChamber.isEtat());
        assertEquals(chamberToUpdate.getBloc(), updatedChamber.getBloc());

        verify(chamberRepository).findByIdChamber(1L);
        verify(chamberRepository).save(chamberToUpdate);
    }


    @Test
    void testFindAll() {
        List<Chamber> expectedChambers = new ArrayList<>();
        expectedChambers.add(Chamber.builder().idChamber(1).numerochamber(101).description("Description 1").etat(true).build());
        expectedChambers.add(Chamber.builder().idChamber(2).numerochamber(102).description("Description 2").etat(false).build());

        when(chamberRepository.findAll()).thenReturn(expectedChambers);

        List<Chamber> actualChambers = chamberService.findAll();

        assertNotNull(actualChambers);
        assertEquals(expectedChambers.size(), actualChambers.size());
        for (int i = 0; i < expectedChambers.size(); i++) {
            assertEquals(expectedChambers.get(i).getIdChamber(), actualChambers.get(i).getIdChamber());
            assertEquals(expectedChambers.get(i).getNumerochamber(), actualChambers.get(i).getNumerochamber());
            assertEquals(expectedChambers.get(i).getDescription(), actualChambers.get(i).getDescription());
            assertEquals(expectedChambers.get(i).isEtat(), actualChambers.get(i).isEtat());
        }

        verify(chamberRepository).findAll();
    }


    @Test
    void testFindById() {
        long id = 1;
        Chamber expectedChamber = Chamber.builder().idChamber(id).numerochamber(101).description("Description 1").etat(true).build();

        when(chamberRepository.findById(id)).thenReturn(Optional.of(expectedChamber));

        Chamber actualChamber = chamberService.findById(id);

        assertNotNull(actualChamber);
        assertEquals(expectedChamber.getIdChamber(), actualChamber.getIdChamber());
        assertEquals(expectedChamber.getNumerochamber(), actualChamber.getNumerochamber());
        assertEquals(expectedChamber.getDescription(), actualChamber.getDescription());
        assertEquals(expectedChamber.isEtat(), actualChamber.isEtat());

        verify(chamberRepository).findById(id);
    }

    @Test
    void testDeleteByID() {
        long idToDelete = 1;

        chamberService.deleteByID(idToDelete);

        verify(chamberRepository).deleteById(idToDelete);
    }

    @Test
    void testDelete() {
        Chamber chamberToDelete = Chamber.builder()
                .idChamber(1)
                .numerochamber(123)
                .description("Description")
                .etat(true)
                .typeC(TypeChamber.SIMPLE)
                .build();

        chamberService.delete(chamberToDelete);

        verify(chamberRepository).delete(chamberToDelete);
    }


    @Test
    void testGetChambresParNomBloc() {
        String nomBloc = "BlocA";
        Bloc bloc = Bloc.builder().nomBloc(nomBloc).build();
        List<Chamber> expectedChambers = Arrays.asList(
                Chamber.builder().idChamber(1).numerochamber(101).bloc(bloc).build(),
                Chamber.builder().idChamber(2).numerochamber(102).bloc(bloc).build()
        );

        when(blocRepository.getBlocByNomBloc(nomBloc)).thenReturn(bloc);

        when(chamberRepository.findByBloc(bloc)).thenReturn(expectedChambers);

        List<Chamber> actualChambers = chamberService.getChambresParNomBloc(nomBloc);

        assertEquals(expectedChambers.size(), actualChambers.size());
        assertTrue(actualChambers.containsAll(expectedChambers));
    }

    @Test
    void testNbChambreParTypeEtBloc() {
        TypeChamber type = TypeChamber.SIMPLE;
        long idBloc = 1L;
        int expectedCount = 5;

        when(chamberRepository.countChamberByTypeCAndBloc_IdBloc(type, idBloc)).thenReturn(expectedCount);

        long actualCount = chamberService.nbChambreParTypeEtBloc(type, idBloc);

        assertEquals(expectedCount, actualCount);
    }

    @Test
    void testGetChambresNonReserveParNomFoyerEtTypeChambre() {
        String nomFoyer = "Example Foyer";
        TypeChamber type = TypeChamber.SIMPLE;
        List<Chamber> expectedChambers = new ArrayList<>();

        when(chamberRepository.findChamberByBlocFoyerNomFoyerAndTypeCAndRes_Empty(nomFoyer, type))
                .thenReturn(expectedChambers);

        List<Chamber> actualChambers = chamberService.getChambresNonReserveParNomFoyerEtTypeChambre(nomFoyer, type);

        assertEquals(expectedChambers, actualChambers);
    }

    @Test
    void testGetChambersByType() {
        TypeChamber type = TypeChamber.SIMPLE;
        List<Chamber> expectedChambers = new ArrayList<>();

        when(chamberRepository.findByTypeC(type))
                .thenReturn(expectedChambers);

        List<Chamber> actualChambers = chamberService.getChambersByType(type);

        assertEquals(expectedChambers, actualChambers);
    }

    @Test
    void testGetChambersByTypeAndBlocName() {
        TypeChamber type = TypeChamber.SIMPLE;
        String blocName = "BlocName";
        List<Chamber> expectedChambers = new ArrayList<>();

        when(chamberRepository.findByTypeCAndBlocNomBloc(type, blocName))
                .thenReturn(expectedChambers);

        List<Chamber> actualChambers = chamberService.getChambersByTypeAndBlocName(type, blocName);

        assertEquals(expectedChambers, actualChambers);
    }

    @Test
    void testFindChamberByBlocFoyerUniversiteNomUniversite() {
        String nomUniversite = "UniversiteName";
        List<Chamber> expectedChambers = new ArrayList<>();

        when(chamberRepository.findChamberByBlocFoyerUniversiteNomUniversite(nomUniversite))
                .thenReturn(expectedChambers);

        List<Chamber> actualChambers = chamberService.findChamberByBlocFoyerUniversiteNomUniversite(nomUniversite);

        assertEquals(expectedChambers, actualChambers);
    }

    @Test
    void testFindAvailableChamberByBlocFoyerUniversiteNomUniversite() {
        String nomUniversite = "UniversiteName";
        List<Chamber> chambers = new ArrayList<>();
        chambers.add(createChamber(1, TypeChamber.SIMPLE, 0));   // No reservations
        chambers.add(createChamber(2, TypeChamber.DOUBLE, 1));   // 1 reservation (maximum for DOUBLE type)
        chambers.add(createChamber(3, TypeChamber.TRIPLE, 2));   // 2 reservations (maximum for TRIPLE type)

        when(chamberRepository.findChamberByBlocFoyerUniversiteNomUniversite(nomUniversite))
                .thenReturn(chambers);

        List<Chamber> availableChambers = chamberService.findAvailableChamberByBlocFoyerUniversiteNomUniversite(nomUniversite);

        assertTrue(availableChambers.stream().anyMatch(chamber -> chamber.getIdChamber() == 1));
        assertTrue(availableChambers.stream().anyMatch(chamber -> chamber.getIdChamber() == 2));
        assertTrue(availableChambers.stream().anyMatch(chamber -> chamber.getIdChamber() == 3));
    }




    private Chamber createChamber(long id, TypeChamber type, int reservationCount) {
        Chamber chamber = new Chamber();
        chamber.setIdChamber(id);
        chamber.setTypeC(type);
        Set<Reservation> reservations = new HashSet<>();
        for (int i = 0; i < reservationCount; i++) {
            Reservation reservation = new Reservation();
            reservation.setEstValide(true);
            reservations.add(reservation);
        }
        chamber.setRes(reservations);
        return chamber;
    }


    @Test
    void testAddChamberReservation() {
        long chamberId = 1L;
        String reservationId = "101L";

        Chamber chamber = new Chamber();
        chamber.setIdChamber(chamberId);
        Reservation reservation = new Reservation();
        reservation.setIdReservation( reservationId);
        when(chamberRepository.findById(chamberId)).thenReturn(Optional.of(chamber));

        when(chamberRepository.save(chamber)).thenReturn(chamber);

        Chamber result = chamberService.addChamberReservation(chamberId, reservation);

        assertTrue(result.getRes().contains(reservation));
    }

    @Test
    void testAffecterBlocAChambre() {
        long chamberId = 1L;
        long blocId = 101L;

        Chamber chamber = new Chamber();
        chamber.setIdChamber(chamberId);
        Bloc bloc = new Bloc();
        bloc.setIdBloc(blocId);

        when(chamberRepository.findById(chamberId)).thenReturn(Optional.of(chamber));
        when(blocRepository.findById(blocId)).thenReturn(Optional.of(bloc));

        assertDoesNotThrow(() -> chamberService.affecterBlocAChambre(chamberId, blocId));

        assertEquals(bloc, chamber.getBloc());
    }





}
