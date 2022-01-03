package se.iths.projektarbetekomplexjava.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.iths.projektarbetekomplexjava.entity.Customer;
import se.iths.projektarbetekomplexjava.service.CustomerService;
import se.iths.projektarbetekomplexjava.service.EmailVerification;

@RestController
@RequestMapping("bokhandel/api/v1/confirmationEmail/")
public class EmailController {

    EmailVerification emailVerification;
    private final CustomerService service;

    public EmailController(EmailVerification emailVerification, CustomerService service) {
        this.emailVerification = emailVerification;
        this.service = service;
    }

    @PostMapping("/sendEmail")
    public ResponseEntity<?> confirmationEmailToUser(@RequestBody Customer customer){
        Customer customerFound = service.findCustomerByEmail(customer.getEmail());

        return emailVerification.sendConfirmationEmail(customerFound.getEmail());
    }
}
