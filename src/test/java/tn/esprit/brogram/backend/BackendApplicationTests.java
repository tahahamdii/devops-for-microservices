package tn.esprit.brogram.backend;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import tn.esprit.brogram.backend.DAO.Entities.Universite;
import tn.esprit.brogram.backend.DAO.Repositories.UniversiteRepository;
import tn.esprit.brogram.backend.Services.UniversiteService;

@SpringBootTest
public class BackendApplicationTests {

	@Autowired
	private UniversiteService universiteService;

	@MockBean
	private UniversiteRepository universiteRepository;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testAddUniversite() {
		// Given
		Universite u = new Universite();
		u.setNomUniversite("Test Universite");

		// Mock the behavior of universiteRepository.save()
		when(universiteRepository.save(u)).thenReturn(u);

		// When
		Universite result = universiteService.addUniversite(u);

		// Then
		assertNotNull(result);
		assertNotEquals(0, result.getIdUniversite());
		assertNotNull(result.getCreatedAt());
		assertNotNull(result.getUpdatedAt());

		// Verify that save method is called with the provided Universite object
		verify(universiteRepository).save(u);
	}
}
