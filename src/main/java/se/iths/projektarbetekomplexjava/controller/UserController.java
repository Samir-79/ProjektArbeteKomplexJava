package se.iths.projektarbetekomplexjava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.iths.projektarbetekomplexjava.dto.UserDTO;
import se.iths.projektarbetekomplexjava.dto.LoginDTO;
import se.iths.projektarbetekomplexjava.email.EmailVerification;
import se.iths.projektarbetekomplexjava.entity.*;
import se.iths.projektarbetekomplexjava.exception.BadRequestException;
import se.iths.projektarbetekomplexjava.jms.Sender;
import se.iths.projektarbetekomplexjava.repository.CustomerRepository;
import se.iths.projektarbetekomplexjava.repository.EmployeeRepository;
import se.iths.projektarbetekomplexjava.repository.RoleRepository;
import se.iths.projektarbetekomplexjava.repository.UserRepository;
import se.iths.projektarbetekomplexjava.security.*;
import se.iths.projektarbetekomplexjava.service.CustomerService;
import se.iths.projektarbetekomplexjava.service.EmployeeService;
import se.iths.projektarbetekomplexjava.service.UserService;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("bokhandel/api/v1/user/")
public class UserController {

    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    AuthenticationManager authenticationManager;
    private final UserService service;
    private final CustomerService customerService;
    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;
    private final EmployeeService employeeService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final PasswordEncoder passwordEncoder;
    private final EmailValidator emailValidator;
    private final EmailVerification emailVerification;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public UserController(UserService service, CustomerService customerService, CustomerRepository customerRepository, EmployeeRepository employeeRepository, EmployeeService employeeService, BCryptPasswordEncoder bCryptPasswordEncoder, PasswordEncoder passwordEncoder, EmailValidator emailValidator, EmailVerification emailVerification, RoleRepository roleRepository, UserRepository userRepository) {
        this.service = service;
        this.customerService = customerService;
        this.customerRepository = customerRepository;
        this.employeeRepository = employeeRepository;
        this.employeeService = employeeService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.passwordEncoder = passwordEncoder;
        this.emailValidator = emailValidator;
        this.emailVerification = emailVerification;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginDTO loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new UserDTO(jwt,
                userDetails.getUsername(),
                roles));
    }




    @PostMapping("signup/customer")
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
                    customer.getAddress(), customer.getPhone());
        } catch (Exception e) {
            e.printStackTrace();
        }
        emailVerification.sendConfirmationEmail(customer.getEmail());


        return new ResponseEntity<>(addedCustomer, HttpStatus.CREATED);
    }

    @PostMapping("signup/employee")
    public ResponseEntity<?> registerCustomer(@RequestBody Employee employee) {

        boolean isValidEmail = emailValidator.test(employee.getEmail());
        List<Employee> employeeList = employeeService.getByEmail(employee.getEmail());
        if (employee.getFirstName().isEmpty() || employee.getLastName().isEmpty() || employee.getAddress().isEmpty()
                || employee.getPhone().isEmpty() || employee.getUsername().isEmpty() || employee.getEmail().isEmpty() || employee.getPassword().isEmpty()) {
            throw new BadRequestException("Insufficient data, please fill the required registration data.");
        } else if (!isValidEmail) {
            throw new BadRequestException("Email: " + employee.getEmail() + " is not valid");
        }
        for (Employee employees : employeeList) {
            if (employee.getUsername().equals(employees.getUsername())) {
                throw new BadRequestException("Username: " + employee.getUsername() + " is already taken.");
            } else if (employee.getEmail().equals(employees.getEmail())) {
                throw new BadRequestException("Email: " + employee.getEmail() + " is already taken.");
            }
        }
        if (PasswordValidator.isValidPassword(employee.getPassword())) {
            employee.setPassword(bCryptPasswordEncoder.encode(employee.getPassword()));
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
                employee.getUsername(),
                employee.getPassword());
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException("Error: Admin Role not found."));
        roles.add(userRole);

        appUser.setRoles(roles);
        userRepository.save(appUser);



        Employee addedEmployee = employeeService.addEmployee(employee);

        try {
            Sender.sendUser(employee.getFirstName(), employee.getLastName(), employee.getEmail(), employee.getUsername(),
                    employee.getAddress(), employee.getPhone());
        } catch (Exception e) {
            e.printStackTrace();
        }
        emailVerification.sendConfirmationEmail(employee.getEmail());


        return new ResponseEntity<>(addedEmployee, HttpStatus.CREATED);
    }






}
