package se.iths.projektarbetekomplexjava.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import se.iths.projektarbetekomplexjava.entity.Customer;
import se.iths.projektarbetekomplexjava.entity.Employee;
import se.iths.projektarbetekomplexjava.entity.Role;
import se.iths.projektarbetekomplexjava.repository.CustomerRepository;
import se.iths.projektarbetekomplexjava.repository.EmployeeRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public EmployeeService(EmployeeRepository employeeRepository,
                           CustomerRepository customerRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.employeeRepository = employeeRepository;
        this.customerRepository = customerRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public Employee addEmployee(Employee employee){
        employee.setPassword(bCryptPasswordEncoder.encode(employee.getPassword()));
        employee.setRole(Role.ADMIN);
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
