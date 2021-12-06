package se.iths.projektarbetekomplexjava.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import se.iths.projektarbetekomplexjava.entity.Customer;
import se.iths.projektarbetekomplexjava.exception.BadRequestException;
import se.iths.projektarbetekomplexjava.exception.NotAuthorizedException;
import se.iths.projektarbetekomplexjava.exception.NotFoundException;
import se.iths.projektarbetekomplexjava.service.CustomerService;

import java.util.Optional;


@RestController
@RequestMapping("api/v1/customer/")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @PostMapping("/signup")
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer){
        Customer addedCustomer = service.addCustomer(customer);
        if (customer.getFirstName() == null || customer.getLastName() == null || customer.getAddress() == null
                || customer.getPhone() == null || customer.getUsername() == null || customer.getEmail() == null
                || customer.getPassword() == null){
            throw new BadRequestException("Missing data" + customer);
        }
        return new ResponseEntity<>(addedCustomer, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Customer> logInCustomer(@RequestBody Customer customer){
        Customer loginCustomer = service.getCustomerByUsername(customer.getUsername(), customer.getPassword());
        if (loginCustomer.getUsername().isEmpty() || loginCustomer.getPassword().isEmpty()){
            throw new NotAuthorizedException("Invalid login. " + loginCustomer);
        }
        return new ResponseEntity<>(loginCustomer, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer){
        Customer updatedCustomer = service.updateCustomer(customer);
        return new ResponseEntity<>(updatedCustomer, HttpStatus.CREATED);
    }
}
