package se.iths.projektarbetekomplexjava.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import se.iths.projektarbetekomplexjava.entity.CustomerEntity;
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
    public ResponseEntity<CustomerEntity> addCustomer(@RequestBody CustomerEntity customer){
        CustomerEntity addedCustomer = service.addCustomer(customer);
        if (customer.getFirstName().isEmpty() || customer.getLastName().isEmpty() || customer.getAddress().isEmpty()
                || customer.getPhone().isEmpty() || customer.getUsername().isEmpty() || customer.getEmail().isEmpty()
                || customer.getPassword().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(addedCustomer, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<CustomerEntity> logInCustomer(@RequestBody CustomerEntity customer){
        CustomerEntity loginCustomer = service.getCustomerByUsername(customer.getUsername(), customer.getPassword());
        return new ResponseEntity<>(loginCustomer, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<CustomerEntity>> findCustomerId(@PathVariable long id){
        Optional<CustomerEntity> foundCustomer = service.findUserById(id);
        return new ResponseEntity<>(foundCustomer, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<CustomerEntity> updateCustomer(@RequestBody CustomerEntity customer){
        CustomerEntity updatedCustomer = service.updateCustomer(customer);
        return new ResponseEntity<>(updatedCustomer, HttpStatus.CREATED);
    }
}
