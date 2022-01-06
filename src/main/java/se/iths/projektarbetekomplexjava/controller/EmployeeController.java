package se.iths.projektarbetekomplexjava.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.iths.projektarbetekomplexjava.entity.Customer;
import se.iths.projektarbetekomplexjava.entity.Employee;
import se.iths.projektarbetekomplexjava.exception.NotAuthorizedException;
import se.iths.projektarbetekomplexjava.exception.NotFoundException;
import se.iths.projektarbetekomplexjava.security.PasswordEncoder;
import se.iths.projektarbetekomplexjava.service.CustomerService;
import se.iths.projektarbetekomplexjava.service.EmployeeService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("bokhandel/api/v1/employee/")
public class EmployeeController {

    private final CustomerService customerService;
    private final EmployeeService employeeService;
    private final PasswordEncoder passwordEncoder;

    public EmployeeController(CustomerService customerService, EmployeeService employeeService, PasswordEncoder passwordEncoder) {
        this.customerService = customerService;
        this.employeeService = employeeService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/signup")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee){
        Employee addedEmployee = employeeService.addEmployee(employee);
        return new ResponseEntity<>(addedEmployee, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Employee> logInEmployee(@RequestBody Employee employee){
        Employee loginEmployee = employeeService.CheckLogIn(employee.getEmail(), employee.getPassword());
        return new ResponseEntity<>(loginEmployee, HttpStatus.OK);
    }

    @PostMapping("/logout/{id}")
    public ResponseEntity<Employee> logOutEmployee(@PathVariable Long id){
        Employee logOutEmployee = employeeService.CheckLogOut(id);
        return new ResponseEntity<>(logOutEmployee, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employee){
        Optional<Employee> foundEmployee = employeeService.findUserById(id);
        Employee updatedEmployee = employeeService.updateEmployee(employee);
        if (foundEmployee.isEmpty()){
            throw new NotFoundException("No data available of user ID: " + id);
        }
        return new ResponseEntity<>(updatedEmployee, HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/deleteEmployee/{id}")
    public ResponseEntity<Void> removeEmployee(@PathVariable Long id){
        Optional<Employee> foundEmployee = employeeService.findUserById(id);
        if (foundEmployee.isEmpty()){
            throw new NotFoundException("No data available of user ID: " + id);
        }
        employeeService.removeEmployee(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/admin/deleteCustomer/{id}")
    public ResponseEntity<Void> removeCustomer(@PathVariable Long id){
        Optional<Customer> foundCustomer = customerService.findUserById(id);
        if (foundCustomer.isEmpty()){
            throw new NotFoundException("No data available of user ID: " + id);
        }
        customerService.removeCustomer(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/admin/getListOfCustomer")
    public ResponseEntity<Iterable<Customer>> getAllCustomers(){
        List<Customer> allCustomers = employeeService.findAllCustomers();
        if (allCustomers.isEmpty()){
            throw new NotFoundException("No data available in the system.");
        }
        return new ResponseEntity<>(allCustomers, HttpStatus.OK);
    }

    @GetMapping("/admin/getListOfEmployee")
    public ResponseEntity<Iterable<Employee>> getAllEmployees(){
        List<Employee> allEmployees = employeeService.findAllEmployees();
        if (allEmployees.isEmpty()){
            throw new NotFoundException("No data available in the system.");
        }
        return new ResponseEntity<>(allEmployees, HttpStatus.OK);
    }

    @GetMapping("/admin/searchEmployee/{id}")
    public ResponseEntity<Optional<Employee>> findEmployeeId(@PathVariable long id){
        Optional<Employee> foundEmployee = employeeService.findUserById(id);
        if (foundEmployee.isEmpty()){
            throw new NotFoundException("No data available of user ID: " + id);
        }
        return new ResponseEntity<>(foundEmployee, HttpStatus.OK);
    }

    @GetMapping("/searchCustomer/{id}")
    public ResponseEntity<Optional<Customer>> findCustomerId(@PathVariable long id){
        Optional<Customer> foundCustomer = customerService.findUserById(id);
        if (foundCustomer.isEmpty()){
            throw new NotFoundException("No data available of user ID: " + id);
        }
        return new ResponseEntity<>(foundCustomer, HttpStatus.OK);
    }
}