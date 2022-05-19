package se.iths.projektarbetekomplexjava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.iths.projektarbetekomplexjava.email.EmailVerification;
import se.iths.projektarbetekomplexjava.entity.AppUser;
import se.iths.projektarbetekomplexjava.entity.Customer;
import se.iths.projektarbetekomplexjava.entity.ERole;
import se.iths.projektarbetekomplexjava.entity.Role;
import se.iths.projektarbetekomplexjava.exception.BadRequestException;
import se.iths.projektarbetekomplexjava.jms.Sender;
import se.iths.projektarbetekomplexjava.repository.CustomerRepository;
import se.iths.projektarbetekomplexjava.repository.RoleRepository;
import se.iths.projektarbetekomplexjava.repository.UserRepository;
import se.iths.projektarbetekomplexjava.security.EmailValidator;
import se.iths.projektarbetekomplexjava.security.JwtUtils;
import se.iths.projektarbetekomplexjava.security.PasswordEncoder;
import se.iths.projektarbetekomplexjava.security.PasswordValidator;
import se.iths.projektarbetekomplexjava.service.CustomerService;
import se.iths.projektarbetekomplexjava.service.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("bokhandel/api/v1/signup/")
public class UserController {

    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    AuthenticationManager authenticationManager;
    private final UserService service;
    private final CustomerService customerService;

    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final PasswordEncoder passwordEncoder;
    private final EmailValidator emailValidator;
    private final EmailVerification emailVerification;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public UserController(UserService service, CustomerService customerService, CustomerRepository customerRepository, BCryptPasswordEncoder bCryptPasswordEncoder, PasswordEncoder passwordEncoder, EmailValidator emailValidator, EmailVerification emailVerification, RoleRepository roleRepository, UserRepository userRepository) {
        this.service = service;
        this.customerService = customerService;
        this.customerRepository = customerRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.passwordEncoder = passwordEncoder;
        this.emailValidator = emailValidator;
        this.emailVerification = emailVerification;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/customer")
    public ResponseEntity<?> registerCustomer(@RequestBody Customer customer) {

        boolean isValidEmail = emailValidator.test(customer.getEmail());
        List<Customer> customerList = customerService.getByEmail(customer.getEmail());
        if (customer.getFirstName().isEmpty() || customer.getLastName().isEmpty() || customer.getAddress().isEmpty()
                || customer.getPhone().isEmpty() || customer.getUsername().isEmpty() || customer.getEmail().isEmpty() || customer.getPassword().isEmpty()) {
            throw new BadRequestException("Insufficient data, please fill the required registration data.");
        } else if (!isValidEmail) {
            throw new BadRequestException("Email: " + customer.getEmail() + " is not valid");
        }
        for (Customer customers : customerList) {
            if (customer.getUsername().equals(customers.getUsername())) {
                throw new BadRequestException("Username: " + customer.getUsername() + " is already taken.");
            } else if (customer.getEmail().equals(customers.getEmail())) {
                throw new BadRequestException("Email: " + customer.getEmail() + " is already taken.");
            }
        }
        if (PasswordValidator.isValidPassword(customer.getPassword())) {
            customer.setPassword(bCryptPasswordEncoder.encode(customer.getPassword()));
        } else {
            throw new BadRequestException("""
                    Password must fulfill the password requirement: It contains at least 8 characters and at most 20 characters.
                    It contains at least one digit.
                    It contains at least one upper case alphabet.
                    It contains at least one lower case alphabet.
                    It contains at least one special character which includes !@#$%&*()-+=^.
                    It does’t contain any white space.""");
        }

        AppUser appUser = new AppUser(
                customer.getUsername(),
                customer.getPassword());
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: User Role not found."));
        roles.add(userRole);

        appUser.setRoles(roles);
        userRepository.save(appUser);


        customer.addShoppingCart(customer.getShoppingCart());
        Customer addedCustomer = customerService.addCustomer(customer);

        try {
            Sender.sendUser(customer.getFirstName(), customer.getLastName(), customer.getEmail(), customer.getUsername(),
                    customer.getAddress(), customer.getPhone(), customer.getRole());
        } catch (Exception e) {
            e.printStackTrace();
        }
        emailVerification.sendConfirmationEmail(customer.getEmail());


        return new ResponseEntity<>(addedCustomer, HttpStatus.CREATED);
    }


}
