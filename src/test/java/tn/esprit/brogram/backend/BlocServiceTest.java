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
import tn.esprit.brogram.backend.dao.entities.Bloc;
import tn.esprit.brogram.backend.dao.entities.Chamber;
import tn.esprit.brogram.backend.dao.entities.TypeChamber;
import tn.esprit.brogram.backend.services.BlocService;
import tn.esprit.brogram.backend.services.ChamberService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
 class BlocServiceTest {

    @Autowired
    BlocService blocService;

    @Autowired
    ChamberService chamberService;

    @Test
    void testAddBloc() {
        Bloc bloc = Bloc.builder().nomBloc("bloc a").capaciteBloc(23).description("this is bloc").status("mawjoud").createdAt(new Date()).updatedAt(new Date()).build();
        Bloc savedBloc=blocService.addBloc(bloc);
        Assertions.assertNotNull(savedBloc.getNomBloc());
        blocService.delete(savedBloc);
    }
    @Test
    void testAddBlocWithNullDescription() {
        Bloc bloc = Bloc.builder().nomBloc("bloc with null description").capaciteBloc(30).status("mawjoud").createdAt(new Date()).updatedAt(new Date()).build();
        Bloc savedBloc = blocService.addBloc(bloc);
        Assertions.assertNotNull(savedBloc.getNomBloc());
        blocService.delete(savedBloc);
    }

    @Test
    void testAddBlocWithNullStatus() {
        Bloc bloc = Bloc.builder().nomBloc("bloc with null status").capaciteBloc(30).description("null status test").createdAt(new Date()).updatedAt(new Date()).build();
        Bloc savedBloc = blocService.addBloc(bloc);
        Assertions.assertNotNull(savedBloc.getNomBloc());
        blocService.delete(savedBloc);
    }
    @Test
    void testAddBlocWithNullNomBloc() {
        Bloc bloc = Bloc.builder().capaciteBloc(30).description("null nomBloc test").status("mawjoud").createdAt(new Date()).updatedAt(new Date()).build();
        Bloc savedBloc = blocService.addBloc(bloc);
        Assertions.assertNull(savedBloc.getNomBloc());
        blocService.delete(savedBloc);
    }
    @Test
    void testAddAllBlocsNotEmptyList() {
        List<Bloc> blocList = Arrays.asList(
                Bloc.builder().nomBloc("Bloc 1").capaciteBloc(20).description("Description 1").status("mawjoud").createdAt(new Date()).updatedAt(new Date()).build(),
                Bloc.builder().nomBloc("Bloc 2").capaciteBloc(25).description("Description 2").status("mawjoud").createdAt(new Date()).updatedAt(new Date()).build()
        );
        List<Bloc> savedBlocs = blocService.addAllBlocs(blocList);
        Assertions.assertFalse(savedBlocs.isEmpty(), "List of saved Blocs should not be empty");
        savedBlocs.forEach(blocService::delete);
    }

    @Test
    void testAddAllBlocsListContainsAll() {
        List<Bloc> blocList = Arrays.asList(
                Bloc.builder().nomBloc("Bloc 1").capaciteBloc(20).description("Description 1").status("mawjoud").createdAt(new Date()).updatedAt(new Date()).build(),
                Bloc.builder().nomBloc("Bloc 2").capaciteBloc(25).description("Description 2").status("mawjoud").createdAt(new Date()).updatedAt(new Date()).build()
        );
        List<Bloc> savedBlocs = blocService.addAllBlocs(blocList);
        Assertions.assertTrue(blocList.containsAll(savedBlocs), "Saved Blocs should contain all Blocs from the input list");
        savedBlocs.forEach(blocService::delete);
    }

    @Test
    void testAddAllBlocsListSize() {
        List<Bloc> blocList = Arrays.asList(
                Bloc.builder().nomBloc("Bloc 1").capaciteBloc(20).description("Description 1").status("mawjoud").createdAt(new Date()).updatedAt(new Date()).build(),
                Bloc.builder().nomBloc("Bloc 2").capaciteBloc(25).description("Description 2").status("mawjoud").createdAt(new Date()).updatedAt(new Date()).build()
        );
        List<Bloc> savedBlocs = blocService.addAllBlocs(blocList);
        Assertions.assertEquals(blocList.size(), savedBlocs.size(), "Size of saved Blocs list should match the size of the input list");
        savedBlocs.forEach(blocService::delete);
    }

    @Test
    void testFindAll() {
        Bloc bloc1 = Bloc.builder().nomBloc("Bloc 1").capaciteBloc(20).description("Description 1").status("mawjoud").createdAt(new Date()).updatedAt(new Date()).build();
        Bloc bloc2 = Bloc.builder().nomBloc("Bloc 2").capaciteBloc(25).description("Description 2").status("mawjoud").createdAt(new Date()).updatedAt(new Date()).build();
        blocService.addBloc(bloc1);
        blocService.addBloc(bloc2);
        List<Bloc> blocs = blocService.findAll();
        Assertions.assertFalse(blocs.isEmpty());
    }
    @Test
    void testFindAllNotEmptyList() {
        Bloc bloc = Bloc.builder().nomBloc("Bloc 1").capaciteBloc(20).description("Description 1").status("mawjoud").createdAt(new Date()).updatedAt(new Date()).build();
        blocService.addBloc(bloc);
        List<Bloc> blocs = blocService.findAll();
        Assertions.assertFalse(blocs.isEmpty(), "List of Blocs should not be empty");
        blocService.delete(bloc);
    }

    @Test
    void testFindByIdExistingBloc() {
        Bloc bloc = Bloc.builder().nomBloc("Bloc to find").capaciteBloc(30).description("To find by ID").status("mawjoud").createdAt(new Date()).updatedAt(new Date()).build();
        blocService.addBloc(bloc);
        Bloc foundBloc = blocService.findById(bloc.getIdBloc());
        Assertions.assertNotNull(foundBloc, "Bloc should be found by ID");
        blocService.delete(bloc);
    }

    @Test
    void testFindByIdNonExistingBloc() {
        Bloc foundBloc = blocService.findById(999L);
        Assertions.assertNull(foundBloc, "Bloc should not be found for non-existing ID");
    }



    @Test
    void testDelete() {
        Bloc bloc = Bloc.builder().nomBloc("Bloc to delete").capaciteBloc(30).description("To be deleted").status("mawjoud").createdAt(new Date()).updatedAt(new Date()).build();
        Bloc savedBloc = blocService.addBloc(bloc);
        blocService.delete(savedBloc);
        Assertions.assertNull(blocService.findById(savedBloc.getIdBloc()));
    }
    @Test
    void testDeleteAndVerifySize() {
        // Create a Bloc to be deleted
        Bloc bloc = Bloc.builder().nomBloc("Bloc to delete").capaciteBloc(30).description("To be deleted").status("mawjoud").createdAt(new Date()).updatedAt(new Date()).build();
        Bloc savedBloc = blocService.addBloc(bloc);
        int initialSize = blocService.findAll().size();
        blocService.delete(savedBloc);
        int finalSize = blocService.findAll().size();
        Assertions.assertEquals(initialSize - 1, finalSize, "Size of the Bloc list should decrease by 1 after deletion");
        Assertions.assertNull(blocService.findById(savedBloc.getIdBloc()), "Deleted Bloc should not be found by ID");
    }

    @Test
    void testEditBloc() {
        Bloc bloc = Bloc.builder().nomBloc("Bloc to edit").capaciteBloc(30).description("To be edited").status("mawjoud").createdAt(new Date()).updatedAt(new Date()).build();
        Bloc savedBloc = blocService.addBloc(bloc);
        savedBloc.setDescription("Edited description");
        Bloc updatedBloc = blocService.editBloc(savedBloc);
        Assertions.assertEquals("Edited description", updatedBloc.getDescription());
    }
    @Test
    void testEditBlocWithNonNullDescription() {
        Bloc bloc = Bloc.builder().nomBloc("Bloc to edit").capaciteBloc(30).description("To be edited").status("mawjoud").createdAt(new Date()).updatedAt(new Date()).build();
        Bloc savedBloc = blocService.addBloc(bloc);
        savedBloc.setDescription("Edited description");
        Bloc updatedBloc = blocService.editBloc(savedBloc);
        Assertions.assertNotNull(updatedBloc.getDescription(), "Edited Bloc should have a non-null description");
    }
    @Test
    void testDeleteByIDSuccessfully() {
        Bloc bloc = Bloc.builder().nomBloc("Bloc to delete").capaciteBloc(30).description("To be deleted").status("mawjoud").createdAt(new Date()).updatedAt(new Date()).build();
        Bloc savedBloc = blocService.addBloc(bloc);
        blocService.deleteByID(savedBloc.getIdBloc());
        Assertions.assertNull(blocService.findById(savedBloc.getIdBloc()), "Bloc should be deleted");
    }
    @Test
    void testDeleteByNonExistingID() {
        Assertions.assertDoesNotThrow(() -> blocService.deleteByID(9999L), "Deleting non-existing Bloc should not throw an exception");
    }
    @Test
    void testGetBlocNameByIdExistingID() {
        Bloc bloc = Bloc.builder().nomBloc("Bloc to get name").capaciteBloc(30).description("To get name").status("mawjoud").createdAt(new Date()).updatedAt(new Date()).build();
        Bloc savedBloc = blocService.addBloc(bloc);
        String blocName = blocService.getBlocNameById(savedBloc.getIdBloc());
        Assertions.assertNotNull(blocName, "Bloc name should not be null");
        Assertions.assertEquals(savedBloc.getNomBloc(), blocName, "Bloc name should match");
    }
    @Test
    void testGetBlocNameByNonExistingID() {
        String blocName = blocService.getBlocNameById(9999L);
        Assertions.assertNull(blocName, "Bloc name should be null for non-existing ID");
    }
    @Test
    void testFindBlocByChamberIdChamberExistingID() {
        Bloc bloc = Bloc.builder().nomBloc("Bloc by Chamber ID").capaciteBloc(30).description("To find by Chamber ID").status("mawjoud").createdAt(new Date()).updatedAt(new Date()).build();
        Bloc savedBloc = blocService.addBloc(bloc);
        Chamber chamber = new Chamber();
        chamber.setNumerochamber(101);
        chamber.setTypeC(TypeChamber.SIMPLE);
        chamber.setDescription("Chamber for testing");
        chamber.setEtat(true);
        chamber.setCreatedAt(new Date());
        chamber.setUpdatedAt(new Date());
        chamber.setBloc(savedBloc);
        chamberService.addChamber(chamber);
        Bloc foundBloc = blocService.findBlocByChamberIdChamber(chamber.getIdChamber());
        Assertions.assertNotNull(foundBloc, "Bloc should be found for existing Chamber ID");
        Assertions.assertEquals(savedBloc.getIdBloc(), foundBloc.getIdBloc(), "Found Bloc should match the saved Bloc");
    }
    @Test
    void testFindBlocByChamberIdChamberNonExistingID() {
        Bloc foundBloc = blocService.findBlocByChamberIdChamber(9999L);
        Assertions.assertNull(foundBloc, "Bloc should be null for non-existing Chamber ID");
    }
    @Test
    void testDoesBlocExistForExistingBloc() {
        String existingBlocName = "Bloc A";
        Bloc bloc = Bloc.builder()
                .nomBloc(existingBlocName)
                .capaciteBloc(30)
                .description("Existing Bloc")
                .status("mawjoud")
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();
        blocService.addBloc(bloc);
        boolean doesExist = blocService.doesBlocExist(existingBlocName);
        Assertions.assertTrue(doesExist, "Bloc should exist, but doesBlocExist returned false");
        blocService.delete(bloc);
    }

    @Test
    void testDoesBlocExistForNonExistingBloc() {
        String nonExistingBlocName = "NonExistingBloc";
        boolean doesExist = blocService.doesBlocExist(nonExistingBlocName);
        Assertions.assertFalse(doesExist, "Bloc should not exist, but doesBlocExist returned true");
    }



}
