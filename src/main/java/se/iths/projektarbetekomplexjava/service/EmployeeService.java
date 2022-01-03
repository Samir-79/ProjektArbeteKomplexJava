package se.iths.projektarbetekomplexjava.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import se.iths.projektarbetekomplexjava.email.EmailValidator;
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
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final PasswordEncoder passwordEncoder;
    private final EmailValidator emailValidator;

    public EmployeeService(EmployeeRepository employeeRepository,
                           CustomerRepository customerRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder, PasswordEncoder passwordEncoder, EmailValidator emailValidator) {
        this.employeeRepository = employeeRepository;
        this.customerRepository = customerRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.passwordEncoder = passwordEncoder;
        this.emailValidator = emailValidator;
    }

    public Employee addEmployee(Employee employee){
        boolean isValidEmail = emailValidator.test(employee.getEmail());
        List<Employee> employeeList = getByEmail(employee.getEmail());
        if (employee.getFirstName().isEmpty() || employee.getLastName().isEmpty() || employee.getAddress().isEmpty()
                || employee.getPhone().isEmpty() || employee.getUsername().isEmpty() || employee.getEmail().isEmpty() || employee.getPassword().isEmpty()){
            throw new BadRequestException("Insufficient data, please fill the required registration data.");
        }
        else if (!isValidEmail){
            throw new BadRequestException("Email: " + employee.getEmail() + " is not valid");
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
            throw new BadRequestException("""
                    Password must fulfill the password requirement: It contains at least 8 characters and at most 20 characters.
                    It contains at least one digit.
                    It contains at least one upper case alphabet.
                    It contains at least one lower case alphabet.
                    It contains at least one special character which includes !@#$%&*()-+=^.
                    It does’t contain any white space.""");
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