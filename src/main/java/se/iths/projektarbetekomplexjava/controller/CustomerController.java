package se.iths.projektarbetekomplexjava.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import se.iths.projektarbetekomplexjava.entity.CustomerEntity;
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
    public ResponseEntity<CustomerEntity> addCustomer(@RequestBody CustomerEntity customer) throws BadRequestException {
        CustomerEntity addedCustomer = service.addCustomer(customer);
        if (customer.getFirstName().isEmpty() || customer.getLastName().isEmpty() || customer.getAddress().isEmpty()
                || customer.getPhone().isEmpty() || customer.getUsername().isEmpty() || customer.getEmail().isEmpty()
                || customer.getPassword().isEmpty()){
            throw new BadRequestException("Missing Data");
        }
        return new ResponseEntity<>(addedCustomer, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<CustomerEntity> logInCustomer(@RequestBody CustomerEntity customer) throws NotAuthorizedException {
        CustomerEntity loginCustomer = service.getCustomerByUsername(customer.getUsername(), customer.getPassword());
        if (loginCustomer.getUsername().isEmpty() || loginCustomer.getPassword().isEmpty()){
            throw new NotAuthorizedException("Invalid login.");
        }
        return new ResponseEntity<>(loginCustomer, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<CustomerEntity>> findCustomerId(@PathVariable long id) throws NotFoundException {
        Optional<CustomerEntity> foundCustomer = service.findUserById(id);
        if (foundCustomer.isEmpty()){
            throw new NotFoundException("Customer: " + foundCustomer + "was not found in the system");
        }
        return new ResponseEntity<>(foundCustomer, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<CustomerEntity> updateCustomer(@RequestBody CustomerEntity customer){
        CustomerEntity updatedCustomer = service.updateCustomer(customer);
        return new ResponseEntity<>(updatedCustomer, HttpStatus.CREATED);
    }
}
