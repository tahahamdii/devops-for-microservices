package tn.esprit.brogram.backend;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.brogram.backend.dao.entities.Chamber;
import tn.esprit.brogram.backend.dao.entities.TypeChamber;
import tn.esprit.brogram.backend.services.ChamberService;

@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
 class ChamberServiceTest {

//    @Mock
 //   @Autowired
 //   private ChamberRepository chamberRepository;

//    @InjectMocks
    @Autowired
    private ChamberService chamberService;

//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }

    @Test
    void testAddChambre(){
        Chamber chamber = Chamber.builder().numerochamber(77).description("anyDescription").etat(true).typeC(TypeChamber.SIMPLE).build();
        Chamber savedChamber = chamberService.addChamber(chamber);
        Assertions.assertNotNull(savedChamber.getIdChamber());
        //chamberService.delete(savedChamber);
    }

//    @Test
//     void testAddChamber() {
//
//        Chamber chamber = new Chamber();
//        chamber.setIdChamber(1L);
//
//        when(chamberRepository.save(any(Chamber.class))).thenReturn(chamber);
//
//        Chamber result = chamberService.addChamber(chamber);
//
//        assertNotNull(result);
//        assertEquals(chamber.getIdChamber(), result.getIdChamber());
//    }

//    @Test
//     void testFindChamberByResIdReservation() {
//        // Mock data
//        String idReservation = "123456";
//        Chamber chamber = new Chamber();
//        chamber.setIdChamber(1L);
//
//        when(chamberRepository.findChamberByResIdReservation(idReservation)).thenReturn(chamber);
//
//        Chamber result = chamberService.findChamberByResIdReservation(idReservation);
//
//        // Assert the result
//        assertNotNull(result);
//        assertEquals(chamber.getIdChamber(), result.getIdChamber());
//    }

}
