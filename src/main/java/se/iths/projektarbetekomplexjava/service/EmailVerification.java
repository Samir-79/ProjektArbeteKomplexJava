package se.iths.projektarbetekomplexjava.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import se.iths.projektarbetekomplexjava.email.EmailServiceImpl;

@Service
public class EmailVerification {

    EmailServiceImpl emailService;

    public EmailVerification(EmailServiceImpl emailService) {
        this.emailService = emailService;
    }

    public ResponseEntity<?> sendConfirmationEmail(String email){
        try{
            emailService.send("projektbokhandel@gmail.com", email, "Signup Confirmation", "Your signup was successful");
            return ResponseEntity.ok("Signup Confirmation sent to " + email);
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Error " + e.getMessage());
        }
    }
}