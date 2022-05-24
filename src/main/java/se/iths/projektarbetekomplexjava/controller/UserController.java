package se.iths.projektarbetekomplexjava.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;
import se.iths.projektarbetekomplexjava.email.EmailVerification;
import se.iths.projektarbetekomplexjava.entity.*;
import se.iths.projektarbetekomplexjava.exception.BadRequestException;
import se.iths.projektarbetekomplexjava.jms.Sender;
import se.iths.projektarbetekomplexjava.repository.CustomerRepository;
import se.iths.projektarbetekomplexjava.repository.EmployeeRepository;
import se.iths.projektarbetekomplexjava.repository.RoleRepository;
import se.iths.projektarbetekomplexjava.repository.UserRepository;
import se.iths.projektarbetekomplexjava.security.EmailValidator;
import se.iths.projektarbetekomplexjava.security.PasswordValidator;
import se.iths.projektarbetekomplexjava.service.CustomerService;
import se.iths.projektarbetekomplexjava.service.EmployeeService;
import se.iths.projektarbetekomplexjava.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("bokhandel/api/v1/user/")
public class UserController {

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


    public UserController( UserService service, CustomerService customerService, CustomerRepository customerRepository, EmployeeRepository employeeRepository, EmployeeService employeeService, BCryptPasswordEncoder bCryptPasswordEncoder, PasswordEncoder passwordEncoder, EmailValidator emailValidator, EmailVerification emailVerification, RoleRepository roleRepository, UserRepository userRepository) {
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
        service.save(appUser);


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

    @GetMapping("token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            try{
                //delete the bearer from header
                String refresh_token=authorizationHeader.substring("Bearer ".length());
                //should be the same with the algorithm that add to the token during creation,
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                //verify the token using the algorithm
                JWTVerifier verifier= JWT.require(algorithm).build();
                //verify the token is valid
                DecodedJWT decodedJWT=verifier.verify(refresh_token);
                //get the username from the subject
                String username=decodedJWT.getSubject();
                //get the user from DB using the username we get from the token
                AppUser user =service.getUser(username);
                String access_token = JWT.create()
                        .withSubject(user.getUserName())
                        .withExpiresAt(new Date(System.currentTimeMillis()+10*60*1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles",user.getRoles().stream()
                                .map(Role::getName).collect(Collectors.toList()))
                        .sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token",access_token);
                tokens.put("refresh_token",refresh_token);
                //return Json tokens
                response.setContentType(APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(),tokens);

            }catch (Exception exception){
                response.setHeader("error",exception.getMessage());
                response.setStatus(FORBIDDEN.value());
                //we can do the first or second choice
                //response.sendError(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message",exception.getMessage());
                //return Json tokens
                response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(),error);


            }
        }
        else {
            throw new RuntimeException("Refresh Token is missing");
        }
    }




}

