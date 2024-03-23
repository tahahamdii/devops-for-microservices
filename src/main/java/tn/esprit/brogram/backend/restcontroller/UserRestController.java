package tn.esprit.brogram.backend.restcontroller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;
import tn.esprit.brogram.backend.dao.entities.Roles;
import tn.esprit.brogram.backend.dao.entities.User;
import tn.esprit.brogram.backend.dao.errors.CustomException;
import tn.esprit.brogram.backend.dao.errors.PasswordDoesNotMatchTheOld;
import tn.esprit.brogram.backend.dao.errors.UserNotFoundException;
import tn.esprit.brogram.backend.dao.model.ChangePasswordRequest;
import tn.esprit.brogram.backend.dao.repositories.UserRepository;
import tn.esprit.brogram.backend.services.AuthService;
import tn.esprit.brogram.backend.services.IUserService;

import java.io.IOException;
import java.util.List;


@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
@RequestMapping("UserRestController")
public class UserRestController {
    IUserService iUserService ;
    private final AuthService authService;
    private final UserRepository userRepo;

    @GetMapping("findUserByEmail/{email}")
    public User getUserByEmail(@PathVariable("email") String email){
        return iUserService.findByEmail(email);
    }

    @GetMapping("findEtudiantByEcoleAndRole/{universite}/{role}")
    public List<User> findEtudiantByEcoleAndRole(@PathVariable("universite") String universite , @PathVariable("role") Roles role){
        return iUserService.findEtudiantByEcoleAndRole(universite,role);
    }

    @PutMapping("updateUser")
    public User editUser( @RequestBody User user){
        return iUserService.updateUser(user);
    }

    @PutMapping("/change-password")
    public void changePassword(@RequestBody ChangePasswordRequest request) {

        if (!authService.isOldPasswordCorrect(request.getEmail(), request.getOldPass())) {
            throw new PasswordDoesNotMatchTheOld("The entered old password does not match the current password");
        }
        iUserService.changePassword(request.getEmail(), request.getOldPass(), request.getNewPass());
    }

    @PutMapping("/reset-password/{email}")
    public ResponseEntity<String> resetPassword(@PathVariable("email") String email, @RequestParam String password) {
        try {
            authService.resetPassword(email, password);
            return ResponseEntity.ok("Password reset successfully");
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }

    @GetMapping("/findUsers")
    public List<User> getUsers(){
        return iUserService.getEtudiantUsers();
    }

    @PutMapping("toggelUser")
    public User enableOrDisable( @RequestParam String email){
        return iUserService.enableOrDisable(email);
    }


    @GetMapping("SendEmail")
    public void sendEmail(@RequestParam("email") String email) {
        User user = iUserService.findByEmail(email);
        if (user.getId() == 0) {
            throw new UserNotFoundException("User not found by the provided email");
        }
    }
        @PostMapping("/uploadImg/{idUser}")
    public User addImg(@RequestParam("file") MultipartFile file , @PathVariable("idUser") long idUser) {

        User user = userRepo.findByIdEquals(idUser);

        try {
            user.setImagebyte(file.getBytes());
            userRepo.save(user);
        } catch (IOException e) {
            throw new CustomException(e.getMessage());
        }
        return user;
    }


}
