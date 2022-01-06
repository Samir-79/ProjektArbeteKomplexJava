package se.iths.projektarbetekomplexjava.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import se.iths.projektarbetekomplexjava.exception.NotAuthorizedException;
import se.iths.projektarbetekomplexjava.repository.LogInRepository;
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
    private final LogInRepository logInRepository;

    public CustomerService(CustomerRepository customerRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder,
                           PasswordEncoder passwordEncoder,
                           EmailValidator emailValidator,
                           EmailVerification emailVerification,
    LogInRepository logInRepository) {
        this.customerRepository = customerRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.passwordEncoder = passwordEncoder;
        this.emailValidator = emailValidator;
        this.emailVerification = emailVerification;
        this.logInRepository = logInRepository;
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
            throw new BadRequestException("""
                    Password must fulfill the password requirement: It contains at least 8 characters and at most 20 characters.
                    It contains at least one digit.
                    It contains at least one upper case alphabet.
                    It contains at least one lower case alphabet.
                    It contains at least one special character which includes !@#$%&*()-+=^.
                    It does’t contain any white space.""");
        }
        customer.setRole(Role.USER);
        customer.changeLogin(customer.getLoggedInCustomer());
        customer.addShoppingCart(customer.getShoppingCart());
        try {
            Sender.sender(customer.getFirstName(), customer.getLastName(), customer.getEmail(), customer.getUsername(),
                    customer.getAddress(), customer.getPhone(), customer.getRole());
        } catch (Exception e) {
            e.printStackTrace();
        }
        emailVerification.sendConfirmationEmail(customer.getEmail());
        return customerRepository.save(customer);
    }

    public void removeCustomer(Long id) {
        Customer foundCustomer = customerRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        customerRepository.deleteById(foundCustomer.getId());
    }

    public List<Customer> getByEmail(String email) {
        return customerRepository.findCustomersByEmail(email);
    }

    public Customer findCustomerByEmail(String email){
        return customerRepository.findCustomerByEmail(email);
    }

    public Customer CheckLogIn(String email, String password){
        Customer loginCustomer = customerRepository.findCustomerByEmail(email);
        try {
            if (passwordEncoder.bCryptPasswordEncoder().matches(password, loginCustomer.getPassword())){
                return customerRepository.save(loginCustomer);
            }
            else {
                throw new NotAuthorizedException("Invalid login, please enter right login data or create new account");
            }
        } finally {
            LoggedIn foundUser = logInRepository.findById(loginCustomer.getLoggedInCustomer().getId()).orElseThrow(EntityNotFoundException::new);
            foundUser.setLoggedIn(true);
            logInRepository.save(foundUser);
        }
    }

    public Customer CheckLogOut(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        LoggedIn foundUser = logInRepository.findById(customer.getLoggedInCustomer().getId()).orElseThrow(EntityNotFoundException::new);
        customer.changeLogin(foundUser);
        foundUser.setLoggedIn(false);
        logInRepository.save(foundUser);
        return customerRepository.save(customer);
    }

    public Optional<Customer> findUserById(Long id) {
        try{
            Receiver.receiver();
        }catch (Exception e){
            e.printStackTrace();
        }
        return customerRepository.findById(id);
    }

    public Customer updateCustomer(Customer customer) {
        return customerRepository.save(customer);
    }
}