package se.iths.projektarbetekomplexjava.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import se.iths.projektarbetekomplexjava.entity.Customer;
import se.iths.projektarbetekomplexjava.entity.Employee;
import se.iths.projektarbetekomplexjava.entity.mailsender.MailConstructor;
import se.iths.projektarbetekomplexjava.exception.BadRequestException;
import se.iths.projektarbetekomplexjava.exception.NotAuthorizedException;
import se.iths.projektarbetekomplexjava.exception.NotFoundException;
import se.iths.projektarbetekomplexjava.security.PasswordEncoder;
import se.iths.projektarbetekomplexjava.service.CustomerService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("bokhandel/api/v1/customer/")
public class CustomerController {

    private final CustomerService service;
    private final PasswordEncoder passwordEncoder;
    private final MailConstructor mailConstructor;
   private final JavaMailSender mailSender;

    public CustomerController(CustomerService service, PasswordEncoder passwordEncoder,MailConstructor mailConstructor,JavaMailSender mailSender) {
        this.service = service;
        this.passwordEncoder = passwordEncoder;
        this.mailConstructor=mailConstructor;
        this.mailSender=mailSender;
    }

    @PostMapping("/signup")
    public ResponseEntity<Customer> addCustomer(HttpServletRequest request, @RequestBody Customer customer) {
        Customer addedCustomer = service.addCustomer(customer);
        if (addedCustomer.getFirstName().isEmpty() || addedCustomer.getLastName().isEmpty() || addedCustomer.getAddress().isEmpty()
                || addedCustomer.getPhone().isEmpty() || addedCustomer.getUsername().isEmpty() || addedCustomer.getEmail().isEmpty() || addedCustomer.getPassword().isEmpty()) {
            throw new BadRequestException("Insufficient data, please fill the required registration data.");
        }
        String appUrl = "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
        SimpleMailMessage email = mailConstructor.emailConfirmation(appUrl, request.getLocale(),customer);
        mailSender.send(email);
        return new ResponseEntity<>(addedCustomer, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public Object logInCustomer(@RequestBody Customer customer) {
        Optional<Customer> loginCustomer = service.getCustomerByEmail(customer.getEmail(), customer.getPassword());
        List<Customer> customerList = service.getByEmail(customer.getEmail());
        for (Customer customers : customerList) {
            if (passwordEncoder.bCryptPasswordEncoder().matches(customer.getPassword(), customers.getPassword())) {
                return customerList;
            }
        }
        return loginCustomer.orElseThrow(() -> new NotAuthorizedException("Invalid login, please enter right login data or create new account"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
        Optional<Customer> foundCustomer = service.findUserById(id);
        Customer updatedCustomer = service.updateCustomer(customer);
        if (foundCustomer.isEmpty()) {
            throw new NotFoundException("No data available of user ID: " + id);
        }
        return new ResponseEntity<>(updatedCustomer, HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteCustomer/{id}")
    public ResponseEntity<Void> removeCustomer(@PathVariable Long id) {
        Optional<Customer> foundCustomer = service.findUserById(id);
        if (foundCustomer.isEmpty()) {
            throw new NotFoundException("No data available of user ID: " + id);
        }
        service.removeCustomer(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}