package se.iths.projektarbetekomplexjava.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import se.iths.projektarbetekomplexjava.entity.CustomerEntity;
import se.iths.projektarbetekomplexjava.exception.NotFoundException;
import se.iths.projektarbetekomplexjava.service.CustomerService;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @PostMapping("/signup")
    public ResponseEntity<CustomerEntity> addCustomer(@RequestBody CustomerEntity customer){
        CustomerEntity addedCustomer = service.addCustomer(customer);
        if (customer.getFirstName().isEmpty() || customer.getLastName().isEmpty() || customer.getAddress().isEmpty()
                || customer.getPhone().isEmpty() || customer.getUsername().isEmpty() || customer.getEmail().isEmpty()
                || customer.getPassword().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing data");
        }
        return new ResponseEntity<>(addedCustomer, HttpStatus.CREATED);
    }

}
