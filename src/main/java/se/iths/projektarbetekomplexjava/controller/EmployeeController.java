package se.iths.projektarbetekomplexjava.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import se.iths.projektarbetekomplexjava.entity.CustomerEntity;
import se.iths.projektarbetekomplexjava.entity.EmployeeEntity;
import se.iths.projektarbetekomplexjava.exception.NotFoundException;
import se.iths.projektarbetekomplexjava.service.CustomerService;
import se.iths.projektarbetekomplexjava.service.EmployeeService;

import java.util.Optional;

@RestController
public class EmployeeController {

    private final CustomerService customerService;
    private final EmployeeService employeeService;

    public EmployeeController(CustomerService customerService, EmployeeService employeeService) {
        this.customerService = customerService;
        this.employeeService = employeeService;
    }

    @PostMapping("/signup")
    public ResponseEntity<EmployeeEntity> addEmployee(@RequestBody EmployeeEntity employee){
        EmployeeEntity addedEmployee = employeeService.addEmployee(employee);
        return new ResponseEntity<>(addedEmployee, HttpStatus.CREATED);
    }

    public ResponseEntity<Void> removeCustomer(@PathVariable Long id){
        customerService.removeCustomer(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

//    @GetMapping("/getListOfCustomer")
//    public ResponseEntity<Iterable<CustomerEntity>> getAllCustomers(){
//        try{
//            Iterable<CustomerEntity> allCustomers = service.findAllCustomers();
//            return new ResponseEntity<>(allCustomers, HttpStatus.OK);
//        } catch (NotFoundException ex){
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No data available", ex);
//        }
//    }
}
