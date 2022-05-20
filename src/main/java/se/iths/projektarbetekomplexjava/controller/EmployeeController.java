package se.iths.projektarbetekomplexjava.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import se.iths.projektarbetekomplexjava.entity.Customer;
import se.iths.projektarbetekomplexjava.entity.Employee;
import se.iths.projektarbetekomplexjava.exception.NotFoundException;
import se.iths.projektarbetekomplexjava.service.CustomerService;
import se.iths.projektarbetekomplexjava.service.EmployeeService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("bokhandel/api/v1/employee/")
public class EmployeeController {

    private final CustomerService customerService;
    private final EmployeeService employeeService;

    public EmployeeController(CustomerService customerService, EmployeeService employeeService) {
        this.customerService = customerService;
        this.employeeService = employeeService;
    }

 /*   @PostMapping("/signup")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee){
        Employee addedEmployee = employeeService.addEmployee(employee);
        return new ResponseEntity<>(addedEmployee, HttpStatus.CREATED);
    }*/

    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee){
        Employee updateEmployee = employeeService.updateEmployee(employee);
        return new ResponseEntity<>(updateEmployee, HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteEmployee/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> removeEmployee(@PathVariable Long id){
        Optional<Employee> foundEmployee = employeeService.findUserById(id);
        if (foundEmployee.isEmpty()){
            throw new NotFoundException("No data available of user ID: " + id);
        }
        employeeService.removeEmployee(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/getListOfCustomer")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Iterable<Customer>> getAllCustomers(){
        List<Customer> allCustomers = employeeService.findAllCustomers();
        if (allCustomers.isEmpty()){
            throw new NotFoundException("No data available in the system.");
        }
        return new ResponseEntity<>(allCustomers, HttpStatus.OK);
    }

    @GetMapping("/getListOfEmployee")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Iterable<Employee>> getAllEmployees(){
        List<Employee> allEmployees = employeeService.findAllEmployees();
        if (allEmployees.isEmpty()){
            throw new NotFoundException("No data available in the system.");
        }
        return new ResponseEntity<>(allEmployees, HttpStatus.OK);
    }

    @GetMapping("/searchEmployee/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Optional<Employee>> findEmployeeId(@PathVariable long id){
        Optional<Employee> foundEmployee = employeeService.findUserById(id);
        if (foundEmployee.isEmpty()){
            throw new NotFoundException("No data available of user ID: " + id);
        }
        return new ResponseEntity<>(foundEmployee, HttpStatus.OK);
    }

    @GetMapping("/searchCustomer/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Optional<Customer>> findCustomerId(@PathVariable long id){
        Optional<Customer> foundCustomer = customerService.findUserById(id);
        if (foundCustomer.isEmpty()){
            throw new NotFoundException("No data available of user ID: " + id);
        }
        return new ResponseEntity<>(foundCustomer, HttpStatus.OK);
    }
}