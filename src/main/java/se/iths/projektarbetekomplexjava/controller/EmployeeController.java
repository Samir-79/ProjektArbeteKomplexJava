package se.iths.projektarbetekomplexjava.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.iths.projektarbetekomplexjava.entity.Customer;
import se.iths.projektarbetekomplexjava.entity.Employee;
import se.iths.projektarbetekomplexjava.exception.BadRequestException;
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
    private PasswordEncoder passwordEncoder;

    public EmployeeController(CustomerService customerService, EmployeeService employeeService, PasswordEncoder passwordEncoder) {
        this.customerService = customerService;
        this.employeeService = employeeService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/signup")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee){
        Employee addedEmployee = employeeService.addEmployee(employee);
        if (addedEmployee.getFirstName().isEmpty() || addedEmployee.getLastName().isEmpty() || addedEmployee.getAddress().isEmpty()
                || addedEmployee.getPhone().isEmpty() || addedEmployee.getUsername().isEmpty() || addedEmployee.getEmail().isEmpty() || addedEmployee.getPassword().isEmpty()){
            throw new BadRequestException("Insufficient data, please fill the required registration data.");
        }
        return new ResponseEntity<>(addedEmployee, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @ResponseBody
    public Object logInCustomer(@RequestBody Employee employee){
        Optional<Employee> loginEmployee = employeeService.getEmployeeByUsername(employee.getUsername(), employee.getPassword());
        List<Employee> employeeList = employeeService.getByEmail(employee.getEmail());
        for (Employee employees:employeeList){
            if (passwordEncoder.bCryptPasswordEncoder().matches(employee.getUsername(), employees.getPassword())){
                return employeeList;
            }
        }
        return loginEmployee.orElseThrow(() -> new NotAuthorizedException("Invalid login, please enter right login data or create new account"));
    }

    @PutMapping("/update")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee){
        Employee updatedEmployee = employeeService.updateEmployee(employee);
        if (updatedEmployee == null){
            throw new NotFoundException("User is not in the system.");
        }
        return new ResponseEntity<>(updatedEmployee, HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteCustomer/{id}")
    public ResponseEntity<Void> removeCustomer(@PathVariable Long id){
        Optional<Employee> foundEmployee = employeeService.findUserById(id);
        if (foundEmployee.isEmpty()){
            throw new NotFoundException("No data available of user ID: " + id);
        }
        employeeService.removeEmployee(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}