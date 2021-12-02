package se.iths.projektarbetekomplexjava.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import se.iths.projektarbetekomplexjava.entity.CustomerEntity;
import se.iths.projektarbetekomplexjava.entity.EmployeeEntity;
import se.iths.projektarbetekomplexjava.entity.RoleEntity;
import se.iths.projektarbetekomplexjava.repository.CustomerRepository;
import se.iths.projektarbetekomplexjava.repository.EmployeeRepository;
import se.iths.projektarbetekomplexjava.repository.RoleRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final CustomerRepository customerRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public EmployeeService(EmployeeRepository employeeRepository,
                           CustomerRepository customerRepository,
                           RoleRepository roleRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.employeeRepository = employeeRepository;
        this.customerRepository = customerRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public EmployeeEntity addEmployee(EmployeeEntity employee){
        employee.setPassword(bCryptPasswordEncoder.encode(employee.getPassword()));
        RoleEntity role = roleRepository.findByRole("ADMIN");
        return employeeRepository.save(employee);
    }

    public void removeEmployee(Long id){
        EmployeeEntity foundEmployee = employeeRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        employeeRepository.deleteById(foundEmployee.getId());
    }

    public Optional<CustomerEntity> findUserById(Long id){
        return customerRepository.findById(id);
    }

    public Iterable<CustomerEntity> findAllCustomers(){
        return customerRepository.findAll();
    }
}
