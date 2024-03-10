package tn.esprit.brogram.backend.services;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.brogram.backend.dao.entities.Bloc;
import tn.esprit.brogram.backend.dao.entities.Chamber;
import tn.esprit.brogram.backend.dao.repositories.BlocRepository;


import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@Service
public class BlocService implements IBlocService{
    BlocRepository blocRepository ;
    @Override
    public Bloc addBloc(Bloc b) {
        return blocRepository.save(b); //ajouter many
    }

    @Override
    public List<Bloc> addAllBlocs(List<Bloc> ls) {
        return blocRepository.saveAll(ls);
    }

    @Override
    public Bloc editBloc(Bloc b) {
        Bloc bloc = blocRepository.findByIdBloc(b.getIdBloc());
        b.setFoyer(bloc.getFoyer());
        return blocRepository.save(b);
    }

    @Override
    public List<Bloc> findAll() {
        return blocRepository.findAll();
    }

    @Override
    public Bloc findById(long id) {

        return blocRepository.findById(id).orElse(null);

    }

    @Override
    public void deleteByID(long id) {
        blocRepository.deleteById(id);
    }

    @Override
    public void delete(Bloc b) {

        blocRepository.delete(b);
    }

    @Override
    public String getBlocNameById(long idBloc) {
        Optional<Bloc> blocOptional = blocRepository.findById(idBloc);
        return blocOptional.map(Bloc::getNomBloc).orElse(null);
    }

    @Override
    public Bloc findBlocByChamberIdChamber(long idChamber) {
        return blocRepository.findBlocByChambers_IdChamber(idChamber);
    }

    //by wiwi
    @Override
    public boolean doesBlocExist(String nomBloc) {
        return blocRepository.existsByNomBloc(nomBloc);
    }


    @Override
    public List<Bloc> findBlocByFoyerIdFoyer(long idFoyer) {
        return blocRepository.findBlocByFoyer_IdFoyer(idFoyer);
    }


    @Override
    public double calculateAverageCapacity(long blocId) {
       Bloc bloc=findById(blocId);
       if(bloc !=null && bloc.getChambers()!=null){
           int totalCapacity=bloc.getChambers().stream().mapToInt(this::getChamberCapacity).sum();
           return (double) totalCapacity/bloc.getChambers().size();
       }
        return 0;
    }

    @Override
    public List<Object[]> countChambersByType(long blocId) {
        return blocRepository.countChambersByType(blocId);
    }


    private int getChamberCapacity(Chamber chamber){
        switch (chamber.getTypeC()){
            case SIMPLE:
                return 1;
            case DOUBLE:
                return 2;
            case TRIPLE:
                return 3;
            default:
                return 0;
        }
    }
}
