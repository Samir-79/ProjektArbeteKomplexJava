package se.iths.projektarbetekomplexjava.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import se.iths.projektarbetekomplexjava.email.EmailServiceImpl;
import se.iths.projektarbetekomplexjava.entity.Customer;
import se.iths.projektarbetekomplexjava.entity.Orders;

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

    public ResponseEntity<?> sendOrderConfirmationEmail(Customer customer, Orders orders){
        try {
            emailService.send("projektbokhandel@gmail.com", customer.getEmail(), "Order Confirmation",
                    "Hello " + customer.getEmail()
                    + " We have received your order, and we will send it to you as soon as possible. "
                    + orders
                    + customer
                    + orders.getCartItemList());
            return ResponseEntity.ok("Order Confirmation sent to " + customer.getEmail());
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Error " + e.getMessage());
        }
    }
}