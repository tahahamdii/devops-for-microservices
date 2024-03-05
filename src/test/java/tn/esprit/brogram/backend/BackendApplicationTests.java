package tn.esprit.brogram.backend;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import tn.esprit.brogram.backend.dao.entities.Documents;
import tn.esprit.brogram.backend.dao.entities.Universite;
import tn.esprit.brogram.backend.dao.repositories.DocumentRepository;
import tn.esprit.brogram.backend.dao.repositories.UniversiteRepository;
import tn.esprit.brogram.backend.services.UniversiteService;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
 class BackendApplicationTests {

	@Autowired
	private UniversiteService universiteService;

	@MockBean
	private UniversiteRepository universiteRepository;
	@MockBean
	private DocumentRepository documentRepository ;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}


	@Test
	 void testAddAllUniversite() {
		List<Universite> universiteList = new ArrayList<>();
		Universite u1 = new Universite();
		Universite u2 = new Universite();

		universiteList.add(u1);
		universiteList.add(u2);

		when(universiteRepository.saveAll(universiteList)).thenReturn(universiteList);

		List<Universite> resultList = universiteService.addAllUniversite(universiteList);

		assertNotNull(resultList);
		assertEquals(universiteList.size(), resultList.size());

		verify(universiteRepository).saveAll(universiteList);
	}

	@Test
	 void testEditUniversite() {
		Universite u = new Universite();

		List<Documents> documentList = new ArrayList<>();
		when(documentRepository.findByUniversiteIdUniversite(u.getIdUniversite())).thenReturn(documentList);

		when(universiteRepository.save(u)).thenReturn(u);

		Universite result = universiteService.editUniversite(u);

		assertNotNull(result);

		verify(universiteRepository).save(u);
		for (Documents documents : documentList) {
			verify(documentRepository).save(documents);
		}
	}

	@Test
	void testFindUniversiteByNomUniversiteAndEmail() {

		String name = "Test Universite";
		String email = "test@example.com";
		Universite expected = new Universite();

		when(universiteRepository.findUniversiteByNomUniversiteAndEmail(name, email)).thenReturn(expected);

		// When
		Universite result = universiteService.findUniversiteByNomUniversiteAndEmail(name, email);


		assertNotNull(result);
		assertEquals(expected, result);

		verify(universiteRepository).findUniversiteByNomUniversiteAndEmail(name, email);
	}

	@Test
	void testUnifindByNomUniv() {

		String nomUniversite = "Test Universite";
		Universite expected = new Universite();

		when(universiteRepository.findUnBynomUniversite(nomUniversite)).thenReturn(expected);

		Universite result = universiteService.unifindByNomUniv(nomUniversite);

		assertNotNull(result);
		assertEquals(expected, result);

		verify(universiteRepository).findUnBynomUniversite(nomUniversite);
	}
	@Test
	void testDownloadDocs() {
		long idUniversite = 1L;
		List<Documents> expected = new ArrayList<>(); // Add expected documents
		when(documentRepository.findByUniversiteIdUniversite(idUniversite)).thenReturn(expected);


		List<Documents> result = universiteService.downloadDocs(idUniversite);

		assertNotNull(result);
		assertEquals(expected, result);

		verify(documentRepository).findByUniversiteIdUniversite(idUniversite);
	}

	@Test
	void testGetPendingUniversites() {

		List<Universite> expected = new ArrayList<>(); // Add expected universities
		when(universiteRepository.findByStatuts("En_attente")).thenReturn(expected);


		List<Universite> result = universiteService.getPendingUniversites();

		assertNotNull(result);
		assertEquals(expected, result);

		verify(universiteRepository).findByStatuts("En_attente");
	}
	@Test
	void testFindUniversiteByEmail() {
		// Given
		String email = "test@example.com";
		Universite expected = new Universite();
		// Mocking repository behavior
		when(universiteRepository.findUniversiteByEmail(email)).thenReturn(expected);

		// When
		Universite result = universiteService.findUniversiteByEmail(email);

		// Then
		assertNotNull(result);
		assertEquals(expected, result);

		// Verify that the repository method is called with the provided parameter
		verify(universiteRepository).findUniversiteByEmail(email);
	}
	@Test
	void testGetAcceptedUniversites() {
		// Given
		List<Universite> expected = new ArrayList<>(); // Add expected universities
		// Mocking repository behavior
		when(universiteRepository.findByStatuts("Accepté")).thenReturn(expected);

		// When
		List<Universite> result = universiteService.getAcceptedUniversites();

		// Then
		assertNotNull(result);
		assertEquals(expected, result);

		// Verify that the repository method is called with the provided parameter
		verify(universiteRepository).findByStatuts("Accepté");
	}


}
