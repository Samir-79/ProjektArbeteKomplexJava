package se.iths.projektarbetekomplexjava.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import se.iths.projektarbetekomplexjava.email.EmailServiceImpl;
import se.iths.projektarbetekomplexjava.entity.Customer;
import se.iths.projektarbetekomplexjava.entity.Orders;

import java.util.Optional;

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

    public void sendOrderConfirmationEmail(Optional<Customer> customer, Optional<Orders> orders){
        try {
            emailService.send("projektbokhandel@gmail.com", customer.get().getEmail(), "Order Confirmation",
                    "Hello " + customer.get().getEmail() + "\n"
                    + " We have received your order, and we will send it to you as soon as possible. \n"
                    + orders.get().getOrderDate()
                    + customer
                    + orders.get().getCartItemList());
            ResponseEntity.ok("Order Confirmation sent to " + customer.get().getEmail());
        }catch (Exception e){
            ResponseEntity.badRequest().body("Error " + e.getMessage());
        }
    }
}