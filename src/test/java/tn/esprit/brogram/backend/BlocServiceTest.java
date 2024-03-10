package tn.esprit.brogram.backend;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.brogram.backend.dao.entities.Bloc;
import tn.esprit.brogram.backend.services.BlocService;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class BlocServiceTest {

    @Autowired
    BlocService blocService;

    @Test
    void testAddBloc() {
        Bloc bloc = Bloc.builder().nomBloc("bloc a").capaciteBloc(23).description("this is bloc").status("mawjoud").createdAt(new Date()).updatedAt(new Date()).build();
        Bloc savedBloc=blocService.addBloc(bloc);
        Assertions.assertNotNull(savedBloc.getIdBloc());
        blocService.delete(savedBloc);
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
    void testDelete() {
        Bloc bloc = Bloc.builder().nomBloc("Bloc to delete").capaciteBloc(30).description("To be deleted").status("mawjoud").createdAt(new Date()).updatedAt(new Date()).build();
        Bloc savedBloc = blocService.addBloc(bloc);
        blocService.delete(savedBloc);
        Assertions.assertNull(blocService.findById(savedBloc.getIdBloc()));
    }
    @Test
    void testEditBloc() {
        Bloc bloc = Bloc.builder().nomBloc("Bloc to edit").capaciteBloc(30).description("To be edited").status("mawjoud").createdAt(new Date()).updatedAt(new Date()).build();
        Bloc savedBloc = blocService.addBloc(bloc);
        savedBloc.setDescription("Edited description");
        Bloc updatedBloc = blocService.editBloc(savedBloc);
        Assertions.assertEquals("Edited description", updatedBloc.getDescription());
    }
}
