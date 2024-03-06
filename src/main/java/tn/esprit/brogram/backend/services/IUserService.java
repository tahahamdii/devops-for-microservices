package tn.esprit.brogram.backend.services;

import tn.esprit.brogram.backend.dao.entities.Roles;
import tn.esprit.brogram.backend.dao.entities.User;

import java.util.List;

public interface IUserService {
     User findByEmail(String email);
     User updateUser (User user);
     List<User> findEtudiantByEcoleAndRole(String schoolName, Roles role);
     void changePassword(String email, String newPassword, String oldPssword);
     List<User> getEtudiantUsers();
     User enableOrDisable(String id);
     void saveVerificationToken(long id,String verfi);
     User findByVerificationToken(String verificationToken);
     void disableInactiveAccounts();
}
