package tn.esprit.brogram.backend;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.brogram.backend.dao.entities.Bloc;
import tn.esprit.brogram.backend.dao.repositories.BlocRepository;
import tn.esprit.brogram.backend.services.BlocService;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = BackendApplicationTests.class)
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
public class BlocServiceTestMock {

    @InjectMocks
    private BlocService blocService;

    @Mock
    private BlocRepository blocRepository;

    @Test
    public void addBloc() {
        Bloc bloc = Bloc.builder().nomBloc("bloc a").capaciteBloc(23).description("this is bloc").status("true").build();
        when(blocRepository.save(bloc)).thenReturn(bloc);
        Bloc savedBloc = blocService.addBloc(bloc);
        Assertions.assertTrue(!Objects.equals(savedBloc.getNomBloc(), ""));
        verify(blocRepository).save(bloc);
    }
    @Test
    public void testFindAll1() {
        Bloc bloc1 = Bloc.builder().nomBloc("Bloc 1").capaciteBloc(10).description("this is bloc 1 ").status("true").build();
        Bloc bloc2 = Bloc.builder().nomBloc("Bloc 2").capaciteBloc(15).description("this is bloc 2 ").status("true").build();
        when(blocRepository.findAll()).thenReturn(Arrays.asList(bloc1, bloc2));
        List<Bloc> allBlocs = blocService.findAll();
        Assertions.assertNotNull(allBlocs);
    }
    @Test
    public void testFindAll2() {
        Bloc bloc1 = Bloc.builder().nomBloc("Bloc 1").capaciteBloc(10).description("this is bloc 1 ").status("true").build();
        Bloc bloc2 = Bloc.builder().nomBloc("Bloc 2").capaciteBloc(15).description("this is bloc 2 ").status("true").build();
        when(blocRepository.findAll()).thenReturn(Arrays.asList(bloc1, bloc2));
        List<Bloc> allBlocs = blocService.findAll();
        Assertions.assertEquals(2, allBlocs.size());
    }
    @Test
    public void testFindAll3() {
        Bloc bloc1 = Bloc.builder().nomBloc("Bloc 1").capaciteBloc(10).description("this is bloc 1 ").status("true").build();
        Bloc bloc2 = Bloc.builder().nomBloc("Bloc 2").capaciteBloc(15).description("this is bloc 2 ").status("true").build();
        when(blocRepository.findAll()).thenReturn(Arrays.asList(bloc1, bloc2));
        List<Bloc> allBlocs = blocService.findAll();
        Assertions.assertEquals("Bloc 1", allBlocs.get(0).getNomBloc());
        Assertions.assertEquals("Bloc 2", allBlocs.get(1).getNomBloc());
    }
    @Test
    public void deleteBloc() {
        Bloc bloc = Bloc.builder().nomBloc("bloc a").capaciteBloc(23).description("this is bloc").status("true").build();
        blocService.delete(bloc);
        Mockito.verify(blocRepository).delete(bloc);
    }

    @Test
    public void editBloc() {
        Bloc existingBloc = Bloc.builder().idBloc(1L).nomBloc("ExistingBloc").capaciteBloc(23).description("this is bloc").status("true").build();
        Mockito.when(blocRepository.findByIdBloc(existingBloc.getIdBloc())).thenReturn(existingBloc);
        Bloc modifiedBloc = Bloc.builder().idBloc(1L).nomBloc("ModifiedBloc").capaciteBloc(23).description("this is bloc").status("true").build();
        blocService.editBloc(modifiedBloc);
        Mockito.verify(blocRepository).findByIdBloc(existingBloc.getIdBloc());
        Mockito.verify(blocRepository).save(modifiedBloc);
    }

}
