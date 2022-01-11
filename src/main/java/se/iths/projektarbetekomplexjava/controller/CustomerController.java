package se.iths.projektarbetekomplexjava.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

//    @PostMapping("/login")
//    public ResponseEntity<Customer> logInCustomer(@RequestBody Customer customer){
//        Customer loginCustomer = service.CheckLogIn(customer.getEmail(), customer.getPassword());
//        return new ResponseEntity<>(loginCustomer, HttpStatus.OK);
//    }
//
//    @PostMapping("/logout/{id}")
//    public ResponseEntity<Customer> logOutCustomer(@PathVariable Long id){
//        Customer logOutCustomer = service.CheckLogOut(id);
//        return new ResponseEntity<>(logOutCustomer, HttpStatus.OK);
//    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer customer){
        Optional<Customer> foundCustomer = service.findUserById(id);
        Customer updatedCustomer = service.updateCustomer(customer);
        if (foundCustomer.isEmpty()){
            throw new NotFoundException("No data available of user ID: " + id);
        }
        return new ResponseEntity<>(updatedCustomer, HttpStatus.CREATED);
    }
}