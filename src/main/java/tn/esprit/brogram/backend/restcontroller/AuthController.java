package tn.esprit.brogram.backend.restcontroller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tn.esprit.brogram.backend.dao.entities.User;
import tn.esprit.brogram.backend.dao.errors.InvalidCredentials;
import tn.esprit.brogram.backend.dao.errors.UserNotEnabled;
import tn.esprit.brogram.backend.dao.errors.UserNotFoundException;
import tn.esprit.brogram.backend.dao.model.LoginRequest;
import tn.esprit.brogram.backend.dao.model.LoginResponce;
import tn.esprit.brogram.backend.dao.model.RegisterDto;
import tn.esprit.brogram.backend.dao.repositories.ImageRepositroy;
import tn.esprit.brogram.backend.dao.repositories.UserRepository;
import tn.esprit.brogram.backend.security.JwtIssuer;
import tn.esprit.brogram.backend.services.AuthService;


@RestController
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    private final JwtIssuer jwtIssuer;
    private final AuthenticationManager authenticationManager;
    private final AuthService authService;
    private final ImageRepositroy imageRepositroy;

    @CrossOrigin(origins = "http://localhost:4200/auth/login", maxAge = 3600, allowCredentials = "true")
    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponce> login(@RequestBody @Validated LoginRequest request) {
        if (request.getEmail() == null || request.getEmail().isEmpty() ||
                request.getPassword() == null || request.getPassword().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        User user = userRepo.findByEmail(request.getEmail());
        if (user == null) {
            throw new UserNotFoundException("User not found by email");
        }
        if (!user.getEnabled()) {
            throw new UserNotEnabled("User is not authorized");
        }
        try {
            LoginResponce loginResponse = authService.attemtptLogin(request.getEmail(), request.getPassword());
            authService.lastLogin(request.getEmail());
            return ResponseEntity.ok(loginResponse);
        } catch (Exception e) {
            throw new InvalidCredentials("Invalid credentials: " + e.getMessage());
        }
    }



        @PostMapping("/auth/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterDto registerDto) {
        User user = userRepo.findByEmail(registerDto.getEmail());
        if (user != null) {
            throw new UserNotFoundException("User exist by that email");
        }
        authService.registerUser(registerDto);
        return ResponseEntity.ok("User registered successfully");
    }



    @GetMapping("/verify")
    public ResponseEntity<String> verifyUser(@RequestParam("token") String verificationToken) {
        if (validateVerificationToken(verificationToken)) {
            return ResponseEntity.ok("User verified successfully");
        } else {
            return ResponseEntity.badRequest().body("Invalid verification token");
        }
    }

    private boolean validateVerificationToken(String verificationToken) {
        User u = userRepo.findByVerificationToken(verificationToken);
        if (u.getVerificationToken().equals(verificationToken)) {
            if (u.getEnabled()) {
                return true;
            }else if(!u.getEnabled()){
                u.setEnabled(true);
                userRepo.save(u);
                return true;
            }
        }
        return false;
    }
}
