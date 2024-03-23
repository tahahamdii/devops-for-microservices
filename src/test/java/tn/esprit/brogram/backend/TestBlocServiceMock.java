package tn.esprit.brogram.backend;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.brogram.backend.dao.entities.Bloc;
import tn.esprit.brogram.backend.dao.repositories.BlocRepository;
import tn.esprit.brogram.backend.services.BlocService;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@TestPropertySource("classpath:application-test.properties")
 class TestBlocServiceMock {

    @InjectMocks
    private BlocService blocService;

    @Mock
    private BlocRepository blocRepository;

    @Test
     void addBloc() {
        Bloc bloc = Bloc.builder().nomBloc("bloc a").capaciteBloc(23).description("this is bloc").status("true").build();
        when(blocRepository.save(bloc)).thenReturn(bloc);
        Bloc savedBloc = blocService.addBloc(bloc);
        Assertions.assertEquals("bloc a",savedBloc.getNomBloc());
        verify(blocRepository).save(bloc);
    }
   @Test
   void testAddBlocWithNullDescription() {
      Bloc bloc = Bloc.builder().nomBloc("bloc with null description").capaciteBloc(30).status("mawjoud").createdAt(new Date()).updatedAt(new Date()).build();
      when(blocRepository.save(bloc)).thenReturn(bloc);
      Bloc savedBloc = blocService.addBloc(bloc);
      Assertions.assertNotNull(savedBloc.getNomBloc());
      Mockito.verify(blocRepository).save(bloc);
   }

   @Test
   void testAddBlocWithNullStatus() {
      Bloc bloc = Bloc.builder().nomBloc("bloc with null status").capaciteBloc(30).description("null status test").createdAt(new Date()).updatedAt(new Date()).build();
      when(blocRepository.save(bloc)).thenReturn(bloc);
      Bloc savedBloc = blocService.addBloc(bloc);
      Assertions.assertNotNull(savedBloc.getNomBloc());
      Mockito.verify(blocRepository).save(bloc);
   }
   @Test
   void testAddBlocWithNullNomBloc() {
      Bloc bloc = Bloc.builder().capaciteBloc(30).description("null nomBloc test").status("mawjoud").createdAt(new Date()).updatedAt(new Date()).build();
      when(blocRepository.save(bloc)).thenReturn(bloc);
      Bloc savedBloc = blocService.addBloc(bloc);
      assertNull(savedBloc.getNomBloc());
      Mockito.verify(blocRepository).save(bloc);
   }
   @Test
   void testAddAllBlocsNotEmptyList() {
      List<Bloc> blocList = Arrays.asList(
              Bloc.builder().nomBloc("Bloc 1").capaciteBloc(20).description("Description 1").status("mawjoud").createdAt(new Date()).updatedAt(new Date()).build(),
              Bloc.builder().nomBloc("Bloc 2").capaciteBloc(25).description("Description 2").status("mawjoud").createdAt(new Date()).updatedAt(new Date()).build()
      );
      when(blocRepository.saveAll(blocList)).thenReturn(blocList);
      List<Bloc> savedBlocs = blocService.addAllBlocs(blocList);
      assertFalse(savedBlocs.isEmpty(), "List of saved Blocs should not be empty");
      Mockito.verify(blocRepository).saveAll(blocList);
   }
   @Test
   void testAddAllBlocsListContainsAll() {
      List<Bloc> blocList = Arrays.asList(
              Bloc.builder().nomBloc("Bloc 1").capaciteBloc(20).description("Description 1").status("mawjoud").createdAt(new Date()).updatedAt(new Date()).build(),
              Bloc.builder().nomBloc("Bloc 2").capaciteBloc(25).description("Description 2").status("mawjoud").createdAt(new Date()).updatedAt(new Date()).build()
      );
      when(blocRepository.saveAll(blocList)).thenReturn(blocList);
      List<Bloc> savedBlocs = blocService.addAllBlocs(blocList);
      Assertions.assertTrue(blocList.containsAll(savedBlocs), "Saved Blocs should contain all Blocs from the input list");
      Mockito.verify(blocRepository).saveAll(blocList);
   }
   @Test
   void testAddAllBlocsListSize() {
      List<Bloc> blocList = Arrays.asList(
              Bloc.builder().nomBloc("Bloc 1").capaciteBloc(20).description("Description 1").status("mawjoud").createdAt(new Date()).updatedAt(new Date()).build(),
              Bloc.builder().nomBloc("Bloc 2").capaciteBloc(25).description("Description 2").status("mawjoud").createdAt(new Date()).updatedAt(new Date()).build()
      );
      when(blocRepository.saveAll(blocList)).thenReturn(blocList);
      List<Bloc> savedBlocs = blocService.addAllBlocs(blocList);
      Assertions.assertEquals(blocList.size(), savedBlocs.size(), "Size of saved Blocs list should match the size of the input list");
      Mockito.verify(blocRepository).saveAll(blocList);
   }
    @Test
     void testFindAll1() {
        Bloc bloc1 = Bloc.builder().nomBloc("Bloc 1").capaciteBloc(10).description("this is bloc 1 ").status("true").build();
        Bloc bloc2 = Bloc.builder().nomBloc("Bloc 2").capaciteBloc(15).description("this is bloc 2 ").status("true").build();
        when(blocRepository.findAll()).thenReturn(Arrays.asList(bloc1, bloc2));
        List<Bloc> allBlocs = blocService.findAll();
        Assertions.assertNotNull(allBlocs);
    }
    @Test
     void testFindAll2() {
        Bloc bloc1 = Bloc.builder().nomBloc("Bloc 1").capaciteBloc(10).description("this is bloc 1 ").status("true").build();
        Bloc bloc2 = Bloc.builder().nomBloc("Bloc 2").capaciteBloc(15).description("this is bloc 2 ").status("true").build();
        when(blocRepository.findAll()).thenReturn(Arrays.asList(bloc1, bloc2));
        List<Bloc> allBlocs = blocService.findAll();
        Assertions.assertEquals(2, allBlocs.size());
    }
    @Test
     void testFindAll3() {
        Bloc bloc1 = Bloc.builder().nomBloc("Bloc 1").capaciteBloc(10).description("this is bloc 1 ").status("true").build();
        Bloc bloc2 = Bloc.builder().nomBloc("Bloc 2").capaciteBloc(15).description("this is bloc 2 ").status("true").build();
        when(blocRepository.findAll()).thenReturn(Arrays.asList(bloc1, bloc2));
        List<Bloc> allBlocs = blocService.findAll();
        Assertions.assertEquals("Bloc 1", allBlocs.get(0).getNomBloc());
        Assertions.assertEquals("Bloc 2", allBlocs.get(1).getNomBloc());
    }
   @Test
   void testFindByIdExistingBloc() {
      Bloc bloc = Bloc.builder().nomBloc("Bloc to find").capaciteBloc(30).description("To find by ID").status("mawjoud").createdAt(new Date()).updatedAt(new Date()).build();
      when(blocRepository.findById(bloc.getIdBloc())).thenReturn(java.util.Optional.of(bloc));
      Bloc foundBloc = blocService.findById(bloc.getIdBloc());
      Assertions.assertNotNull(foundBloc, "Bloc should be found by ID");
      verify(blocRepository).findById(bloc.getIdBloc());
   }
   @Test
   void testFindByIdNonExistingBloc() {
      when(blocRepository.findById(999L)).thenReturn(java.util.Optional.empty());
      Bloc foundBloc = blocService.findById(999L);
      assertNull(foundBloc, "Bloc should not be found for non-existing ID");
      verify(blocRepository).findById(999L);
   }
    @Test
     void deleteBloc() {
        Bloc bloc = Bloc.builder().nomBloc("bloc a").capaciteBloc(23).description("this is bloc").status("true").build();
        blocService.delete(bloc);
        Mockito.verify(blocRepository).delete(bloc);
    }
   @Test
    void testDeleteByIDSuccessfully() {
      Bloc bloc = Bloc.builder().nomBloc("Bloc to delete").capaciteBloc(30).description("To be deleted").status("mawjoud").createdAt(new Date()).updatedAt(new Date()).build();
      when(blocRepository.save(any(Bloc.class))).thenReturn(bloc);
      Bloc savedBloc = blocService.addBloc(bloc);
      blocService.deleteByID(savedBloc.getIdBloc());
      verify(blocRepository, times(1)).deleteById(savedBloc.getIdBloc());
      assertNull(blocService.findById(savedBloc.getIdBloc()), "Bloc should be deleted");
   }

   @Test
    void testDeleteByNonExistingID() {
      doNothing().when(blocRepository).deleteById(9999L);
      assertDoesNotThrow(() -> blocService.deleteByID(9999L), "Deleting non-existing Bloc should not throw an exception");
   }
   @Test
   void editBloc() {
      Bloc existingBloc = Bloc.builder().idBloc(1L).nomBloc("ExistingBloc").capaciteBloc(23).description("this is bloc").status("true").build();
      Mockito.when(blocRepository.findByIdBloc(existingBloc.getIdBloc())).thenReturn(existingBloc);
      Bloc modifiedBloc = Bloc.builder().idBloc(1L).nomBloc("ModifiedBloc").capaciteBloc(23).description("this is bloc").status("true").build();
      blocService.editBloc(modifiedBloc);
      Mockito.verify(blocRepository).findByIdBloc(existingBloc.getIdBloc());
      Mockito.verify(blocRepository).save(modifiedBloc);
   }

   @Test
    void testGetBlocNameByNonExistingID() {
      when(blocRepository.findById(9999L)).thenReturn(Optional.empty());
      String blocName = blocService.getBlocNameById(9999L);
      assertNull(blocName, "Bloc name should be null for non-existing ID");
   }
   @Test
    void testFindBlocByChamberIdChamberNonExistingID() {
      when(blocRepository.findBlocByChambers_IdChamber(9999L)).thenReturn(null);
      Bloc foundBloc = blocService.findBlocByChamberIdChamber(9999L);
      assertNull(foundBloc, "Bloc should be null for non-existing Chamber ID");
   }
   @Test
    void testDoesBlocExistForNonExistingBloc() {
      String nonExistingBlocName = "NonExistingBloc";
      when(blocRepository.existsByNomBloc(nonExistingBlocName)).thenReturn(false);
      boolean doesExist = blocService.doesBlocExist(nonExistingBlocName);
      assertFalse(doesExist, "Bloc should not exist, but doesBlocExist returned true");
   }

}
