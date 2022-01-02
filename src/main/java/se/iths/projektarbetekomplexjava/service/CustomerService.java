package se.iths.projektarbetekomplexjava.service;

import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import se.iths.projektarbetekomplexjava.email.EmailSender;
import se.iths.projektarbetekomplexjava.entity.*;
import se.iths.projektarbetekomplexjava.exception.BadRequestException;
import se.iths.projektarbetekomplexjava.jms.Receiver;
import se.iths.projektarbetekomplexjava.jms.Sender;
import se.iths.projektarbetekomplexjava.repository.BookRepository;
import se.iths.projektarbetekomplexjava.repository.CustomerRepository;
import se.iths.projektarbetekomplexjava.repository.ShoppingCartRepository;
import se.iths.projektarbetekomplexjava.security.PasswordEncoder;
import se.iths.projektarbetekomplexjava.security.PasswordValidator;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final PasswordEncoder passwordEncoder;
    private final ShoppingCartRepository shoppingCartRepository;
    private final BookRepository bookRepository;

    public CustomerService(CustomerRepository customerRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder, ShoppingCartRepository shoppingCartRepository,
                           BookRepository bookRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.shoppingCartRepository = shoppingCartRepository;
        this.bookRepository = bookRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Customer addCustomer(Customer customer) {
        List<Customer> customerList = getByEmail(customer.getEmail());
        if (customer.getFirstName().isEmpty() || customer.getLastName().isEmpty() || customer.getAddress().isEmpty()
                || customer.getPhone().isEmpty() || customer.getUsername().isEmpty() || customer.getEmail().isEmpty() || customer.getPassword().isEmpty()){
            throw new BadRequestException("Insufficient data, please fill the required registration data.");
        }
        for (Customer customers:customerList){
            if(passwordEncoder.bCryptPasswordEncoder().matches(customer.getPassword(), customers.getPassword())){
                throw new BadRequestException("Password: " + customer.getPassword() + " is already taken.");
            }
            else if(customer.getUsername().equals(customers.getUsername())){
                throw new BadRequestException("Username: " + customer.getUsername() + " is already taken.");
            }
            else if(customer.getEmail().equals(customers.getEmail())){
                throw new BadRequestException("Email: " + customer.getEmail() + " is already taken.");
            }
        }
        if(PasswordValidator.isValidPassword(customer.getPassword())){
            customer.setPassword(bCryptPasswordEncoder.encode(customer.getPassword()));
        }
        else {
            throw new IllegalStateException("Password must fulfill the password requirement");
        }
        customer.setRole(Role.USER);
        customer.addShoppingCart(customer.getShoppingCart());
        try {
            Sender.sender(customer.getFirstName(), customer.getLastName(), customer.getEmail(), customer.getUsername(),
                    customer.getAddress(), customer.getPhone(), customer.getRole());
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        try{
            Receiver.receiver();
        }catch (Exception e){
            e.printStackTrace();
        }
        return customerRepository.findById(id);
    }

    public Optional<Customer> getCustomerByEmail(String email, String password) {
        return customerRepository.findCustomerByEmailAndPassword(email, password);
    }

    public Customer updateCustomer(Customer customer) {
        return customerRepository.save(customer);
    }
}