package se.iths.projektarbetekomplexjava.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) throws BadRequestException {
        Customer addedCustomer = service.addCustomer(customer);
        if (customer.getFirstName().isEmpty() || customer.getLastName().isEmpty() || customer.getAddress().isEmpty()
                || customer.getPhone().isEmpty() || customer.getUsername().isEmpty() || customer.getEmail().isEmpty()
                || customer.getPassword().isEmpty()){
            throw new BadRequestException("Missing Data");
        }
        return new ResponseEntity<>(addedCustomer, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Customer> logInCustomer(@RequestBody Customer customer) throws NotAuthorizedException {
        Customer loginCustomer = service.getCustomerByUsername(customer.getUsername(), customer.getPassword());
        if (loginCustomer.getUsername().isEmpty() || loginCustomer.getPassword().isEmpty()){
            throw new NotAuthorizedException("Invalid login.");
        }
        return new ResponseEntity<>(loginCustomer, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Customer>> findCustomerId(@PathVariable long id) throws NotFoundException {
        Optional<Customer> foundCustomer = service.findUserById(id);
        if (foundCustomer.isEmpty()){
            throw new NotFoundException("Customer: " + foundCustomer + "was not found in the system");
        }
        return new ResponseEntity<>(foundCustomer, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer){
        Customer updatedCustomer = service.updateCustomer(customer);
        return new ResponseEntity<>(updatedCustomer, HttpStatus.CREATED);
    }
}
