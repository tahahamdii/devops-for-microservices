    package tn.esprit.brogram.backend.services;
    import lombok.AllArgsConstructor;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.stereotype.Service;
    import org.webjars.NotFoundException;
    import tn.esprit.brogram.backend.dao.entities.*;
    import tn.esprit.brogram.backend.dao.repositories.DocumentRepository;
    import tn.esprit.brogram.backend.dao.repositories.UniversiteRepository;
    import java.util.*;
    import java.util.concurrent.TimeUnit;

    @AllArgsConstructor
    @Service
    @Slf4j
    public class UniversiteService implements IUniversiteService{
        UniversiteRepository universiteRepository ;
        DocumentRepository documentRepository ;
        @Override
        public Universite addUniversite(Universite u) {
            u.setCreatedAt(new Date());
            u.setUpdatedAt(new Date());
            return universiteRepository.save(u);
        }

        @Override
        public List<Universite> addAllUniversite(List<Universite> ls) {
            return universiteRepository.saveAll(ls);
        }


        @Override
        public Universite editUniversite(Universite u) {
            List<Documents> documentList ;
            documentList = documentRepository.findByUniversiteIdUniversite(u.getIdUniversite());
            universiteRepository.save(u);
            for (Documents documents : documentList) {
                documents.setUniversite(u);
                documentRepository.save(documents);
            }
            return u;
        }

        @Override
        public List<Universite> unifindAll() {
            return universiteRepository.findAll();
        }

        @Override
        public Universite unifindById(long id) {
            return universiteRepository.findById(id).orElse(Universite.builder().build());
        }

        @Override
        public void unideleteById(long id) {
            universiteRepository.deleteById(id);
        }

        @Override
        public void unidelete(Universite u) {
            universiteRepository.delete(u);
        }

        @Override
        public Universite updateStatus(long id, String status) {

            Universite universite = universiteRepository.findById(id).orElse(Universite.builder().build());
            universite.setCreatedAt(new Date());
            universite.setStatuts(status);
            return universiteRepository.save(universite);
        }

        @Override
        public Universite findUniversiteByEmail(String email) {
            return universiteRepository.findUniversiteByEmail(email);
        }

        @Override
        public List<Universite> getAcceptedUniversites() {
            return universiteRepository.findByStatuts("Accepté");

        }

        @Override
        public Universite affecterFoyerAUniversite(long idFoyer, String nomUniversite) {
            Universite u = universiteRepository.findUnBynomUniversite(nomUniversite);
            u.setCreatedAt(new Date());
            universiteRepository.save(u);
            return u;
        }

        @Override
        public Universite desaffecterFoyerAUniversite(long idUniversite) {
            Universite u = universiteRepository.findById(idUniversite)
                    .orElseThrow(() -> new NotFoundException("Universite with id " + idUniversite + " not found"));


            universiteRepository.save(u);
            return u;    }



        @Override
        public Universite findUniversiteByNomUniversiteAndEmail(String name, String email) {
            return universiteRepository.findUniversiteByNomUniversiteAndEmail(name,email);
        }


        @Override
        public Universite unifindByNomUniv(String nomUniversite) {
            Universite universite = universiteRepository.findUnBynomUniversite(nomUniversite);
            return universite != null ? universite : Universite.builder().build();    }

        @Override
        public List<Documents> downloadDocs(long idUniversite) {
            return documentRepository.findByUniversiteIdUniversite(idUniversite);

        }

        @Override
        public List<Universite> getPendingUniversites() {
            return universiteRepository.findByStatuts("En_attente");
        }



       // @Scheduled(cron = "*/10 * * * * *")
        public void updateStatusIfPendingForMoreThan5Minutes() {
            List<Universite> pendingUniversites = universiteRepository.findByStatuts("En_attente");

            for (Universite universite : pendingUniversites) {
                Date createdAt = universite.getCreatedAt();

                if (createdAt != null) {
                    Date now = new Date();

                    long timeDifference = now.getTime() - createdAt.getTime();
                    long secondsElapsed = TimeUnit.MILLISECONDS.toSeconds(timeDifference);
                    log.info("ID: {}, createdAt: {}, secondsElapsed: {}", universite.getIdUniversite(), createdAt, secondsElapsed);

                    if (secondsElapsed > 10) {
                        universite.setStatuts("desactivée");
                        log.info("OOK DESSSSS");
                        universiteRepository.save(universite);
                    }
                } else {
                    log.warn("Skipping Universite with null createdAt: {}", universite.getIdUniversite());
                }
            }
        }


    }



