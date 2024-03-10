package tn.esprit.brogram.backend;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.brogram.backend.dao.entities.Bloc;
import tn.esprit.brogram.backend.dao.repositories.BlocRepository;
import tn.esprit.brogram.backend.services.BlocService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


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
     void deleteBloc() {
        Bloc bloc = Bloc.builder().nomBloc("bloc a").capaciteBloc(23).description("this is bloc").status("true").build();
        blocService.delete(bloc);
        Mockito.verify(blocRepository).delete(bloc);
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

}
