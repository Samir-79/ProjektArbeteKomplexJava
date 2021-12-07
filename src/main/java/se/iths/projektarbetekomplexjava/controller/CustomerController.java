package se.iths.projektarbetekomplexjava.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.iths.projektarbetekomplexjava.entity.Customer;
import se.iths.projektarbetekomplexjava.exception.BadRequestException;
import se.iths.projektarbetekomplexjava.exception.NotAuthorizedException;
import se.iths.projektarbetekomplexjava.exception.NotFoundException;
import se.iths.projektarbetekomplexjava.service.CustomerService;

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
            return new ResponseEntity<>(addedCustomer, HttpStatus.CREATED);

    }

    @PostMapping("/login")
    public ResponseEntity<Customer> logInCustomer(@RequestBody Customer customer){
            Customer loginCustomer = service.getCustomerByUsername(customer.getUsername(), customer.getPassword());
            return new ResponseEntity<>(loginCustomer, HttpStatus.OK);
}

    @PutMapping("/update")
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer){
            Customer updatedCustomer = service.updateCustomer(customer);
            return new ResponseEntity<>(updatedCustomer, HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteCustomer/{id}")
    public ResponseEntity<Void> removeCustomer(@PathVariable Long id){
        service.removeCustomer(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
