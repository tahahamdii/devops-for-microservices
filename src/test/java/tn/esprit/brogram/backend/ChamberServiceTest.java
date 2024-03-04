package tn.esprit.brogram.backend;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import tn.esprit.brogram.backend.DAO.Entities.Chamber;
import tn.esprit.brogram.backend.DAO.Entities.TypeChamber;
import tn.esprit.brogram.backend.DAO.Repositories.ChamberRepository;
import tn.esprit.brogram.backend.Services.ChamberService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ChamberServiceTest {

    @Mock
    private ChamberRepository chamberRepository;

    @InjectMocks
    private ChamberService chamberService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddChamber() {

        Chamber chamber = new Chamber();
        chamber.setIdChamber(1L);

        when(chamberRepository.save(any(Chamber.class))).thenReturn(chamber);

        Chamber result = chamberService.addChamber(chamber);

        assertNotNull(result);
        assertEquals(chamber.getIdChamber(), result.getIdChamber());
    }

    @Test
    public void testFindChamberByResIdReservation() {
        // Mock data
        String idReservation = "123456";
        Chamber chamber = new Chamber();
        chamber.setIdChamber(1L);

        when(chamberRepository.findChamberByResIdReservation(idReservation)).thenReturn(chamber);

        Chamber result = chamberService.findChamberByResIdReservation(idReservation);

        // Assert the result
        assertNotNull(result);
        assertEquals(chamber.getIdChamber(), result.getIdChamber());
    }

}
