package se.iths.projektarbetekomplexjava.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import se.iths.projektarbetekomplexjava.entity.Customer;
import se.iths.projektarbetekomplexjava.entity.Employee;
import se.iths.projektarbetekomplexjava.entity.Role;
import se.iths.projektarbetekomplexjava.exception.BadRequestException;
import se.iths.projektarbetekomplexjava.jms.Receiver;
import se.iths.projektarbetekomplexjava.jms.Sender;
import se.iths.projektarbetekomplexjava.repository.CustomerRepository;
import se.iths.projektarbetekomplexjava.repository.EmployeeRepository;
import se.iths.projektarbetekomplexjava.security.PasswordEncoder;
import se.iths.projektarbetekomplexjava.security.PasswordValidator;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final PasswordEncoder passwordEncoder;

    public EmployeeService(EmployeeRepository employeeRepository,
                           CustomerRepository customerRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder, PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.customerRepository = customerRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.passwordEncoder = passwordEncoder;
    }

    public Employee addEmployee(Employee employee){
        List<Employee> employeeList = getByEmail(employee.getEmail());
        if (employee.getFirstName().isEmpty() || employee.getLastName().isEmpty() || employee.getAddress().isEmpty()
                || employee.getPhone().isEmpty() || employee.getUsername().isEmpty() || employee.getEmail().isEmpty() || employee.getPassword().isEmpty()){
            throw new BadRequestException("Insufficient data, please fill the required registration data.");
        }
        for (Employee employees:employeeList){
            if(passwordEncoder.bCryptPasswordEncoder().matches(employee.getPassword(), employees.getPassword())){
                throw new BadRequestException("Password: " + employee.getPassword() + " is already taken.");
            }
            else if(employee.getUsername().equals(employees.getUsername())){
                throw new BadRequestException("Username: " + employee.getUsername() + " is already taken.");
            }
            else if(employee.getEmail().equals(employees.getEmail())){
                throw new BadRequestException("Email: " + employee.getEmail() + " is already taken.");
            }
        }
        if(PasswordValidator.isValidPassword(employee.getPassword())){
            employee.setPassword(bCryptPasswordEncoder.encode(employee.getPassword()));
        }
        else {
            throw new IllegalStateException("Password must fulfill the password requirement");
        }
        employee.setRole(Role.ADMIN);
        try {
            Sender.sender(employee.getFirstName(), employee.getLastName(), employee.getEmail(), employee.getUsername(),
                    employee.getAddress(), employee.getPhone(), employee.getRole());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employeeRepository.save(employee);
    }

    public void removeEmployee(Long id){
        Employee foundEmployee = employeeRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        employeeRepository.deleteById(foundEmployee.getId());
    }

    public List<Employee> getByEmail(String email){
        return employeeRepository.findEmployeeByEmail(email);
    }

    public Optional<Employee> findUserById(Long id){
        try{
            Receiver.receiver();
        }catch (Exception e){
            e.printStackTrace();
        }
        return employeeRepository.findById(id);
    }

    public List<Customer> findAllCustomers(){
        return (List<Customer>) customerRepository.findAll();
    }

    public List<Employee> findAllEmployees(){
        return (List<Employee>) employeeRepository.findAll();
    }

    public Optional<Employee> getEmployeeByEmail(String email, String password) {
        return employeeRepository.findEmployeeByEmailAndPassword(email, password);
    }

    public Employee updateEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }
}