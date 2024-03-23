package tn.esprit.brogram.backend.services;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import tn.esprit.brogram.backend.dao.entities.Reservation;
import tn.esprit.brogram.backend.dao.entities.Roles;
import tn.esprit.brogram.backend.dao.entities.User;
import tn.esprit.brogram.backend.dao.errors.CustomException;
import tn.esprit.brogram.backend.dao.repositories.UserRepository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;


@Service
public class UserService implements IUserService {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }


    public User findByEmail(String email) {
        if(this.userRepo.findByEmail(email) != null){

            return this.userRepo.findByEmail(email);
        }else{
            User user = new User();
            user.setId(0L);
            return user ;
        }
    }

    @JsonIgnore
    @Override
    public User updateUser(User user) {
        if (userRepo.existsById(user.getId())) {
            User existing = userRepo.getReferenceById(user.getId());
            user.setPassword(existing.getPassword());
            user.setEmail(existing.getEmail());
            user.setRole(existing.getRole());
            user.setUpdatedAt(new Date());
            user.setReservations(existing.getReservations());
            return userRepo.save(user);
        } else {
            throw new CustomException("User not found with id: " + user.getId());
        }
    }

    @Override
    public List<User> findEtudiantByEcoleAndRole(String schoolName, Roles role) {
        List<User> etudiants = this.userRepo.findEtudiantByEcoleAndRole(schoolName,role);
        List<User> finalEtudiants = new ArrayList<>();
        for(User etudiant : etudiants){
            boolean test = false ;
            Set<Reservation> reservations = etudiant.getReservations();

            for (Reservation reservation : reservations){
                boolean b = reservation.getEstValide();

                if (b && reservation.getAnneeReservation().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getYear() == LocalDate.now().getYear()) {
                    test = true ;
                }
            }
            if(!test){
                finalEtudiants.add(etudiant);
            }
        }
        return finalEtudiants;
    }

    @Override
    public void saveVerificationToken(long id, String verfi) {
        User u = userRepo.getReferenceById(id);
        u.setVerificationToken(verfi);
        userRepo.save(u);
    }

    @Override
    public User findByVerificationToken(String verificationToken) {
        return userRepo.findByVerificationToken(verificationToken);
    }


    @Override
    public List<User> getEtudiantUsers() {
        return userRepo.findAll();
    }



    @Override
    public User enableOrDisable(String email) {
        User u = userRepo.findByEmail(email);
        if (u != null) {
            boolean isEnabled = u.getEnabled();
            u.setEnabled(!isEnabled);
            userRepo.save(u);
            return u;
        } else {
            throw new NotFoundException("User not found with email: " + email);
        }
    }

    @Override
    public void changePassword(String email, String oldPassword, String newPassword) {
        if(userRepo.existsByEmail(email)) {
            User user = userRepo.findByEmail(email);
            if (passwordEncoder.matches(oldPassword, user.getPassword())) {
                user.setPassword(passwordEncoder.encode(newPassword));
                userRepo.save(user);
            } else {
                throw new BadCredentialsException("Incorrect old password");
            }
        }
    }

    @Scheduled(cron = "0 0 0 * * ?")
    @Override
    public void disableInactiveAccounts() {
        List<User> inactiveUsers = userRepo.findByLastLoginBefore(LocalDate.now().minusDays(90));
        for (User user : inactiveUsers) {
            user.setEnabled(false);
            userRepo.save(user);
        }
    }





}
