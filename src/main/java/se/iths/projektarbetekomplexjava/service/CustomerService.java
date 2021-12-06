package se.iths.projektarbetekomplexjava.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import se.iths.projektarbetekomplexjava.entity.Customer;
import se.iths.projektarbetekomplexjava.entity.Role;
import se.iths.projektarbetekomplexjava.repository.CustomerRepository;
import se.iths.projektarbetekomplexjava.repository.RoleRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public CustomerService(CustomerRepository customerRepository,
                           RoleRepository roleRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.customerRepository = customerRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public Customer addCustomer(Customer customer){
        customer.setPassword(bCryptPasswordEncoder.encode(customer.getPassword()));
        Role role = roleRepository.findByRole("USER");
        customer.addRole(role);
        return customerRepository.save(customer);
    }

    public void removeCustomer(Long id){
        Customer foundCustomer = customerRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        customerRepository.deleteById(foundCustomer.getId());
    }

    public Optional<Customer> findUserById(Long id){
        return customerRepository.findById(id);
    }

    public Customer getCustomerByUsername(String username, String password){
        return customerRepository.findByUsernameAndPassword(username, password);
    }

    public Customer updateCustomer(Customer customer){
        return customerRepository.save(customer);
    }

}
