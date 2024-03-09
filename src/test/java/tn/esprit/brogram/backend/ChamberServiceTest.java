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

    @Autowired
    private ChamberService chamberService;

    @Test
    void testAddChambre(){
        Chamber chamber = Chamber.builder().numerochamber(77).description("anyDescription").etat(true).typeC(TypeChamber.SIMPLE).build();
        Chamber savedChamber = chamberService.addChamber(chamber);
        Assertions.assertNotNull(savedChamber.getIdChamber());
        //chamberService.delete(savedChamber);
    }



}
