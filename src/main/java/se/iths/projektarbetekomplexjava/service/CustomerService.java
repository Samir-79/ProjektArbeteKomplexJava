package se.iths.projektarbetekomplexjava.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import se.iths.projektarbetekomplexjava.email.EmailVerification;
import se.iths.projektarbetekomplexjava.exception.NotAuthorizedException;
import se.iths.projektarbetekomplexjava.exception.NotFoundException;
import se.iths.projektarbetekomplexjava.security.EmailValidator;
import se.iths.projektarbetekomplexjava.entity.*;
import se.iths.projektarbetekomplexjava.exception.BadRequestException;
import se.iths.projektarbetekomplexjava.jms.Receiver;
import se.iths.projektarbetekomplexjava.jms.Sender;
import se.iths.projektarbetekomplexjava.repository.CustomerRepository;
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
    private final EmailValidator emailValidator;
    private final EmailVerification emailVerification;

    public CustomerService(CustomerRepository customerRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder,
                           PasswordEncoder passwordEncoder,
                           EmailValidator emailValidator,
                           EmailVerification emailVerification) {
        this.customerRepository = customerRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.passwordEncoder = passwordEncoder;
        this.emailValidator = emailValidator;
        this.emailVerification = emailVerification;
    }

    public Customer addCustomer(Customer customer) {
        boolean isValidEmail = emailValidator.test(customer.getEmail());
        List<Customer> customerList = getByEmail(customer.getEmail());
        if (customer.getFirstName().isEmpty() || customer.getLastName().isEmpty() || customer.getAddress().isEmpty()
                || customer.getPhone().isEmpty() || customer.getUsername().isEmpty() || customer.getEmail().isEmpty() || customer.getPassword().isEmpty()){
            throw new BadRequestException("Insufficient data, please fill the required registration data.");
        }
        else if (!isValidEmail){
            throw new BadRequestException("Email: " + customer.getEmail() + " is not valid");
        }
        for (Customer customers:customerList){
            if(customer.getUsername().equals(customers.getUsername())){
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
            throw new BadRequestException("""
                    Password must fulfill the password requirement: It contains at least 8 characters and at most 20 characters.
                    It contains at least one digit.
                    It contains at least one upper case alphabet.
                    It contains at least one lower case alphabet.
                    It contains at least one special character which includes !@#$%&*()-+=^.
                    It does’t contain any white space.""");
        }
        customer.setRole(Role.ROLE_USER);
        customer.addShoppingCart(customer.getShoppingCart());
        try {
            Sender.sendUser(customer.getFirstName(), customer.getLastName(), customer.getEmail(), customer.getUsername(),
                    customer.getAddress(), customer.getPhone(), customer.getRole());
        } catch (Exception e) {
            e.printStackTrace();
        }
        emailVerification.sendConfirmationEmail(customer.getEmail());
        return customerRepository.save(customer);
    }

    public void removeCustomer(Long id) {
        Customer foundCustomer = customerRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        try{
            Receiver.receiveUser();
        }catch (Exception e){
            e.printStackTrace();
        }
        customerRepository.deleteById(foundCustomer.getId());
    }

    public List<Customer> getByEmail(String email) {
        return customerRepository.findCustomersByEmail(email);
    }

    public Optional<Customer> findUserById(Long id) {
        return customerRepository.findById(id);
    }

    public Customer updateCustomer(Customer customer) {
        Customer foundCustomer = customerRepository.findById(customer.getId()).orElseThrow(() -> new NotFoundException("employee not found"));
        List<Customer> customerList= (List<Customer>) customerRepository.findAll();

        if(!(foundCustomer.getEmail().equals(customer.getEmail()))){
            throw new NotAuthorizedException("you are not allowed to change the email");
        }

        else if (customer.getFirstName().isEmpty() || customer.getLastName().isEmpty() || customer.getAddress().isEmpty()
                || customer.getPhone().isEmpty() || customer.getUsername().isEmpty() || customer.getEmail().isEmpty() || customer.getPassword().isEmpty()) {
            throw new BadRequestException("Insufficient data, please fill the required registration data.");
        }

        for (Customer customers:customerList ) {
         if (customers.getUsername().equals(customer.getUsername())) {
                if(!(customers.getUsername().equals(foundCustomer.getUsername())))
                    throw new BadRequestException("Username: " + customer.getUsername() + " is already taken.");
            }
        }

        if (PasswordValidator.isValidPassword(customer.getPassword())) {
            customer.setPassword(bCryptPasswordEncoder.encode(customer.getPassword()));
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
        customer.setRole(Role.ROLE_USER);
        try {
            Sender.sendUser(customer.getFirstName(), customer.getLastName(), customer.getEmail(), customer.getUsername(),
                    customer.getAddress(), customer.getPhone(), customer.getRole());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customerRepository.save(customer);
    }
}