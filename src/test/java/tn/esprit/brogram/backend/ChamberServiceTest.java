package tn.esprit.brogram.backend;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.brogram.backend.dao.entities.*;
import tn.esprit.brogram.backend.dao.repositories.BlocRepository;
import tn.esprit.brogram.backend.dao.repositories.ChamberRepository;
import tn.esprit.brogram.backend.dao.repositories.ReservationRepository;
import tn.esprit.brogram.backend.services.ChamberService;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class ChamberServiceTest {

    @Autowired
    private ChamberService chamberService;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ChamberRepository chamberRepository;

    @Autowired
    private BlocRepository blocRepository;

    @Test
    void testAddChambre() {
        Chamber chamber = Chamber.builder()
                .numerochamber(77)
                .description("anyDescription")
                .etat(true)
                .typeC(TypeChamber.SIMPLE)
                .build();

        Chamber savedChamber = chamberService.addChamber(chamber);

        assertNotNull(savedChamber.getIdChamber());

        Chamber retrievedChamber = chamberRepository.findById(savedChamber.getIdChamber()).orElse(null);
        assertNotNull(retrievedChamber);
        assertEquals(savedChamber.getIdChamber(), retrievedChamber.getIdChamber());
        assertEquals(savedChamber.getNumerochamber(), retrievedChamber.getNumerochamber());
        assertEquals(savedChamber.getDescription(), retrievedChamber.getDescription());
        assertEquals(savedChamber.isEtat(), retrievedChamber.isEtat());
        assertEquals(savedChamber.getTypeC(), retrievedChamber.getTypeC());

        chamberRepository.delete(retrievedChamber);
    }

    @Test
    void testFindChamberByResIdReservation() {
        Chamber chamber = Chamber.builder()
                .numerochamber(77)
                .description("anyDescription")
                .etat(true)
                .typeC(TypeChamber.SIMPLE)
                .res(new HashSet<>())
                .build();
        chamber = chamberRepository.save(chamber);

        Reservation reservation = new Reservation();
        reservation.setIdReservation("yy");

        chamber.getRes().add(reservation);

        reservationRepository.save(reservation);
        chamberRepository.save(chamber);

        String reservationId = chamber.getRes().iterator().next().getIdReservation();

        chamberService.deleteByID(chamber.getIdChamber());
        Chamber foundChamber = chamberService.findChamberByResIdReservation(reservationId);

        assertNull(foundChamber);
        assertFalse(chamberRepository.existsById(chamber.getIdChamber()));
        assertFalse(reservationRepository.existsById(reservation.getIdReservation()));
    }


    @Test
    void testAddAllChambers() {
        List<Chamber> chambersToAdd = new ArrayList<>();
        chambersToAdd.add(Chamber.builder().numerochamber(1).description("Description 1").etat(true).typeC(TypeChamber.SIMPLE).build());
        chambersToAdd.add(Chamber.builder().numerochamber(2).description("Description 2").etat(false).typeC(TypeChamber.DOUBLE).build());
        chambersToAdd.add(Chamber.builder().numerochamber(3).description("Description 3").etat(true).typeC(TypeChamber.TRIPLE).build());

        List<Chamber> addedChambers = chamberService.addAllChambers(chambersToAdd);

        List<Chamber> allChambers = chamberRepository.findAll();
        System.out.println("NUMBER OF SHAMBRES = "+allChambers.size());

        assertEquals(chambersToAdd.size(), addedChambers.size(), "Number of added chambers does not match");

        chamberRepository.deleteAll(addedChambers);
    }

    @Test
    void testEditChamber() {
        Chamber initialChamber = Chamber.builder()
                .numerochamber(1)
                .description("Initial description")
                .etat(true)
                .typeC(TypeChamber.SIMPLE)
                .build();
        initialChamber = chamberRepository.save(initialChamber);

        initialChamber.setDescription("Updated description");

        Chamber updatedChamber = chamberService.editChamber(initialChamber);

        Chamber retrievedChamber = chamberRepository.findById(initialChamber.getIdChamber()).orElse(null);

        assertNotNull(retrievedChamber);
        assertEquals(initialChamber.getDescription(), retrievedChamber.getDescription());

        chamberRepository.delete(retrievedChamber);
    }

    @Test
    void testFindAll() {
        Chamber chamber1 = createChamber(1, TypeChamber.SIMPLE);
        Chamber chamber2 = createChamber(2, TypeChamber.DOUBLE);
        Chamber chamber3 = createChamber(3, TypeChamber.TRIPLE);
        chamberRepository.save(chamber1);
        chamberRepository.save(chamber2);
        chamberRepository.save(chamber3);

        List<Chamber> allChambers = chamberService.findAll();

        assertNotNull(allChambers);

        assertEquals(3, allChambers.size());


        chamberRepository.deleteAll(List.of(chamber1, chamber2, chamber3));
    }

    private Chamber createChamber(long id, TypeChamber type) {
        return Chamber.builder()
                .idChamber(id)
                .typeC(type)
                .build();
    }

    @Test
    void testFindById() {
        Chamber chamber = Chamber.builder()
                .idChamber(1)
                .numerochamber(101)
                .build();
        chamberRepository.save(chamber);

        Chamber foundChamber = chamberService.findById(1);

        assertNotNull(foundChamber);
        assertEquals(0, foundChamber.getIdChamber());

        chamberRepository.delete(foundChamber);
    }

    @Test
    void testDeleteByID() {
        Chamber chamber = Chamber.builder()
                .idChamber(1L)
                .numerochamber(101)
                .build();
        chamberRepository.save(chamber);

        chamberService.deleteByID(1);

        assertFalse(chamberRepository.existsById(1L));
    }


    @Test
    void testDelete() {
        Chamber chamber = Chamber.builder()
                .idChamber(1)
                .numerochamber(101)
                .build();
        chamberRepository.save(chamber);

        chamberService.delete(chamber);

        assertFalse(chamberRepository.existsById(1L));
    }

    @Test
    void testGetChambresParNomBloc() {
        Bloc bloc = new Bloc();
        bloc.setIdBloc(1L);
        bloc.setNomBloc("TestBloc");
        bloc.setCreatedAt(new Date());
        bloc.setUpdatedAt(new Date());

        blocRepository.save(bloc);

        List<Chamber> expectedChambers = new ArrayList<>();
        expectedChambers.add(Chamber.builder().numerochamber(1).description("Description 1").etat(true).typeC(TypeChamber.SIMPLE).build());
        expectedChambers.add(Chamber.builder().numerochamber(2).description("Description 2").etat(false).typeC(TypeChamber.DOUBLE).build());
        for (Chamber chamber : expectedChambers) {
            chamber.setBloc(bloc);
            chamberRepository.save(chamber);
        }

        List<Chamber> actualChambers = chamberService.getChambresParNomBloc("TestBloc");

        assertEquals(expectedChambers.size(), actualChambers.size());

        chamberRepository.deleteAll(expectedChambers);
        blocRepository.delete(bloc);
    }

    @Test
    void testNbChambreParTypeEtBloc() {
        Bloc bloc = new Bloc();
        bloc.setIdBloc(1L);
        bloc.setCreatedAt(new Date());
        bloc.setUpdatedAt(new Date());

        blocRepository.save(bloc);

        Chamber chamber1 = Chamber.builder().typeC(TypeChamber.SIMPLE).bloc(bloc).build();
        Chamber chamber2 = Chamber.builder().typeC(TypeChamber.DOUBLE).bloc(bloc).build();
        Chamber chamber3 = Chamber.builder().typeC(TypeChamber.TRIPLE).bloc(bloc).build();

        chamberRepository.save(chamber1);
        chamberRepository.save(chamber2);
        chamberRepository.save(chamber3);

        long count = chamberService.nbChambreParTypeEtBloc(TypeChamber.SIMPLE, bloc.getIdBloc());

        assertEquals(1, count);
        chamberRepository.deleteAll(Arrays.asList(chamber1, chamber2, chamber3));
        blocRepository.delete(bloc);
    }

    @Test
    void testGetChambresNonReserveParNomFoyerEtTypeChambre() {
        Bloc bloc = new Bloc();
        bloc.setIdBloc(1L);
        bloc.setCreatedAt(new Date());
        bloc.setUpdatedAt(new Date());

        blocRepository.save(bloc);

        Chamber chamber1 = Chamber.builder().typeC(TypeChamber.SIMPLE).bloc(bloc).build();
        Chamber chamber2 = Chamber.builder().typeC(TypeChamber.DOUBLE).bloc(bloc).build();

        chamberRepository.save(chamber1);
        chamberRepository.save(chamber2);

        List<Chamber> resultChambers = chamberService.getChambresNonReserveParNomFoyerEtTypeChambre("TestFoyer", TypeChamber.SIMPLE);

        assertEquals(0, resultChambers.size());

        chamberRepository.deleteAll(Arrays.asList(chamber1, chamber2));
        blocRepository.delete(bloc);
    }

    @Test
    void testGetChambersByType() {
        Chamber chamber1 = Chamber.builder().typeC(TypeChamber.SIMPLE).build();
        Chamber chamber2 = Chamber.builder().typeC(TypeChamber.DOUBLE).build();

        chamberRepository.save(chamber1);
        chamberRepository.save(chamber2);

        List<Chamber> resultChambers = chamberService.getChambersByType(TypeChamber.SIMPLE);

        assertEquals(1, resultChambers.size());
        chamberRepository.deleteAll(Arrays.asList(chamber1, chamber2));
    }

    @Test
    void testGetChambersByTypeAndBlocName() {
        Bloc bloc = Bloc.builder().nomBloc("TestBloc").build();
        bloc.setCreatedAt(new Date());
        bloc.setUpdatedAt(new Date());
        blocRepository.save(bloc);


        Chamber chamber1 = Chamber.builder().typeC(TypeChamber.SIMPLE).bloc(bloc).build();
        Chamber chamber2 = Chamber.builder().typeC(TypeChamber.DOUBLE).bloc(bloc).build();

        chamberRepository.save(chamber1);
        chamberRepository.save(chamber2);

        List<Chamber> resultChambers = chamberService.getChambersByTypeAndBlocName(TypeChamber.SIMPLE, "TestBloc");

        assertEquals(1, resultChambers.size());

        chamberRepository.deleteAll(Arrays.asList(chamber1, chamber2));
        blocRepository.delete(bloc);
    }

    @Test
    void testFindAvailableChamberByBlocFoyerUniversiteNomUniversite() {
        String nomUniversite = "Test University";

        List<Chamber> availableChambers = chamberService.findAvailableChamberByBlocFoyerUniversiteNomUniversite(nomUniversite);

        assertNotNull(availableChambers);

        for (Chamber chamber : availableChambers) {
            assertTrue(isChamberAvailable(chamber));
            assertEquals(nomUniversite, chamber.getBloc().getFoyer().getUniversite().getNomUniversite());
        }
    }

    private boolean isChamberAvailable(Chamber chamber) {
        Set<Reservation> reservations = chamber.getRes();
        int validatedReservationCount = (int) reservations.stream().filter(Reservation::getEstValide).count();

        return switch (chamber.getTypeC()) {
            case SIMPLE -> validatedReservationCount == 0;
            case DOUBLE -> validatedReservationCount <= 1;
            case TRIPLE -> validatedReservationCount <= 2;
        };
    }
    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @Transactional
    void testAddChamberReservations() {
        Reservation reservation = new Reservation();
        reservation.setIdReservation("Test reservation details");

        Chamber chamber = new Chamber();
        chamberRepository.save(chamber);

        chamberService.addChamberReservation(chamber.getIdChamber(), reservation);

        Chamber updatedChamber = chamberRepository.findById(chamber.getIdChamber()).orElse(null);
        assertNotNull(updatedChamber);
        assertFalse(updatedChamber.getRes().contains(reservation));
    }


    @Test
    void testAffecterBlocAChambre() {
        Bloc bloc = new Bloc();
        bloc.setCreatedAt(new Date());
        bloc.setUpdatedAt(new Date());
        blocRepository.save(bloc);

        Chamber chamber = new Chamber();
        chamberRepository.save(chamber);

        chamberService.affecterBlocAChambre(chamber.getIdChamber(), bloc.getIdBloc());

        Chamber updatedChamber = chamberRepository.findById(chamber.getIdChamber()).orElse(null);
        assertNotNull(updatedChamber);
        assertNotEquals(bloc, updatedChamber.getBloc());
    }

    @AfterEach
    void cleanUp() {
        chamberRepository.deleteAll();
        blocRepository.deleteAll();
    }











}






