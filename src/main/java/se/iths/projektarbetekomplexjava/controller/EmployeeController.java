package se.iths.projektarbetekomplexjava.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.iths.projektarbetekomplexjava.entity.Customer;
import se.iths.projektarbetekomplexjava.entity.Employee;
import se.iths.projektarbetekomplexjava.exception.BadRequestException;
import se.iths.projektarbetekomplexjava.exception.NotAuthorizedException;
import se.iths.projektarbetekomplexjava.exception.NotFoundException;
import se.iths.projektarbetekomplexjava.service.CustomerService;
import se.iths.projektarbetekomplexjava.service.EmployeeService;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/employee/")
public class EmployeeController {

    private final CustomerService customerService;
    private final EmployeeService employeeService;

    public EmployeeController(CustomerService customerService, EmployeeService employeeService) {
        this.customerService = customerService;
        this.employeeService = employeeService;
    }

    @PostMapping("/signup")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) throws BadRequestException {
        Employee addedEmployee = employeeService.addEmployee(employee);
        if (employee.getFirstName().isEmpty() || employee.getLastName().isEmpty() || employee.getAddress().isEmpty()
                || employee.getPhone().isEmpty() || employee.getUsername().isEmpty() || employee.getEmail().isEmpty()
                || employee.getPassword().isEmpty()){
            throw new BadRequestException("Missing data");
        }
        return new ResponseEntity<>(addedEmployee, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Employee> logInEmployee(@RequestBody Employee employee) throws NotAuthorizedException{
        Employee loginEmployee = employeeService.getEmployeeByUsername(employee.getUsername(), employee.getPassword());
        if (loginEmployee.getUsername().isEmpty() || loginEmployee.getPassword().isEmpty()){
            throw new NotAuthorizedException("Invalid login.");
        }
        return new ResponseEntity<>(loginEmployee, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Employee>> findEmployeeId(@PathVariable long id) throws NotFoundException{
        Optional<Employee> foundEmployee = employeeService.findUserById(id);
        if (foundEmployee.isEmpty()){
            throw new NotFoundException("Employee: " + foundEmployee + "was not found in the system");
        }
        return new ResponseEntity<>(foundEmployee, HttpStatus.OK);

    }

    @DeleteMapping("/deleteCustomer/{id}")
    public ResponseEntity<Void> removeCustomer(@PathVariable Long id) throws NotFoundException{
        Optional<Customer> foundCustomer = customerService.findUserById(id);
        customerService.removeCustomer(id);
        if (foundCustomer.isEmpty()){
            throw new NotFoundException("Missing data");
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/deleteEmployee/{id}")
    public ResponseEntity<Void> removeEmployee(@PathVariable Long id) throws NotFoundException{
        Optional<Employee> foundEmployee = employeeService.findUserById(id);
        employeeService.removeEmployee(id);
        if (foundEmployee.isEmpty()){
            throw new NotFoundException("Missing data");
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/getListOfCustomer")
    public ResponseEntity<Iterable<Customer>> getAllCustomers() throws NotFoundException{
        Iterable<Customer> allCustomers = employeeService.findAllCustomers();
        if (allCustomers == null){
            throw new NotFoundException("Missing data");
        }
        return new ResponseEntity<>(allCustomers, HttpStatus.OK);
    }
}
