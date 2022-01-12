package se.iths.projektarbetekomplexjava.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import se.iths.projektarbetekomplexjava.entity.Customer;
import se.iths.projektarbetekomplexjava.exception.NotFoundException;
import se.iths.projektarbetekomplexjava.service.CustomerService;

import java.util.Optional;

@RestController
@RequestMapping("bokhandel/api/v1/customer/")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @PostMapping("/signup")
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer){
            Customer addedCustomer = service.addCustomer(customer);
            return new ResponseEntity<>(addedCustomer, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer customer){
        Optional<Customer> foundCustomer = service.findUserById(id);
        Customer updatedCustomer = service.updateCustomer(customer);
        if (foundCustomer.isEmpty()){
            throw new NotFoundException("No data available of user ID: " + id);
        }
        return new ResponseEntity<>(updatedCustomer, HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteCustomer/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> removeCustomer(@PathVariable Long id){
        Optional<Customer> foundCustomer = service.findUserById(id);
        if (foundCustomer.isEmpty()){
            throw new NotFoundException("No data available of user ID: " + id);
        }
        service.removeCustomer(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}