package tn.esprit.brogram.backend.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.brogram.backend.dao.entities.Documents;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Documents,Long> {
    List<Documents> findByUniversiteIdUniversite(long idUniversite);

}
