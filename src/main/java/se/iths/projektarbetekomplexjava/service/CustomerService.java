package se.iths.projektarbetekomplexjava.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import se.iths.projektarbetekomplexjava.entity.*;
import se.iths.projektarbetekomplexjava.repository.BookRepository;
import se.iths.projektarbetekomplexjava.repository.CustomerRepository;
import se.iths.projektarbetekomplexjava.repository.ShoppingCartRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ShoppingCartRepository shoppingCartRepository;
    private final BookRepository bookRepository;

    public CustomerService(CustomerRepository customerRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder, ShoppingCartRepository shoppingCartRepository,
                           BookRepository bookRepository) {
        this.customerRepository = customerRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.shoppingCartRepository = shoppingCartRepository;
        this.bookRepository = bookRepository;
    }

    public Customer addCustomer(Customer customer) {
        customer.setPassword(bCryptPasswordEncoder.encode(customer.getPassword()));
        customer.setRole(Role.USER);
        // customer.addShoppingCart(customer.getShoppingCart());
        return customerRepository.save(customer);

    }

    public void removeCustomer(Long id) {
        Customer foundCustomer = customerRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        customerRepository.deleteById(foundCustomer.getId());
    }

    public List<Customer> getByEmail(String email) {
        return customerRepository.findCustomerByEmail(email);
    }

    public Optional<Customer> findUserById(Long id) {
        return customerRepository.findById(id);
    }

    public Optional<Customer> getCustomerByEmail(String email, String password) {
        return customerRepository.findCustomerByEmailAndPassword(email, password);
    }


    public Customer updateCustomer(Customer customer) {
        return customerRepository.save(customer);
    }


}
