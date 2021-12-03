package se.iths.projektarbetekomplexjava.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import se.iths.projektarbetekomplexjava.entity.CustomerEntity;
import se.iths.projektarbetekomplexjava.entity.RoleEntity;
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

    public CustomerEntity addCustomer(CustomerEntity customer){
        customer.setPassword(bCryptPasswordEncoder.encode(customer.getPassword()));
        RoleEntity role = roleRepository.findByRole("USER");
        customer.addRole(role);
        return customerRepository.save(customer);
    }

    public void removeCustomer(Long id){
        CustomerEntity foundCustomer = customerRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        customerRepository.deleteById(foundCustomer.getId());
    }

    public Optional<CustomerEntity> findUserById(Long id){
        return customerRepository.findById(id);
    }

    public CustomerEntity getCustomerByUsername(String username, String password){
        return customerRepository.findByUsernameAndPassword(username, password);
    }

    public CustomerEntity updateCustomer(CustomerEntity customer){
        return customerRepository.save(customer);
    }

}
