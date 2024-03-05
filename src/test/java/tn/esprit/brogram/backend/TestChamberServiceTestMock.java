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
import tn.esprit.brogram.backend.dao.repositories.ChamberRepository;
import tn.esprit.brogram.backend.services.ChamberService;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class TestChamberServiceTestMock {

    @InjectMocks
    private ChamberService chamberService;

    @Mock
    ChamberRepository chamberRepository;

    @Test
    void testAddChambre(){
        Chamber chamber = Chamber.builder().numerochamber(77).description("anyDescription").etat(true).typeC(TypeChamber.SIMPLE).build();

        Mockito.when(chamberRepository.save(Mockito.any(Chamber.class))).thenReturn(chamber);

        Chamber savedChamber = chamberService.addChamber(chamber);
        Assertions.assertNotNull(savedChamber.getIdChamber());
        Assertions.assertNotNull(savedChamber.getDescription());
        verify(chamberRepository).save(Mockito.any(Chamber.class));


    }

}
