package se.iths.projektarbetekomplexjava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import se.iths.projektarbetekomplexjava.dto.CustomerDTO;
import se.iths.projektarbetekomplexjava.dto.LoginDTO;
import se.iths.projektarbetekomplexjava.entity.Customer;
import se.iths.projektarbetekomplexjava.exception.NotFoundException;
import se.iths.projektarbetekomplexjava.security.CustomerPrincipal;
import se.iths.projektarbetekomplexjava.security.JwtUtils;
import se.iths.projektarbetekomplexjava.service.CustomerService;


import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("bokhandel/api/v1/customer/")
public class CustomerController {


    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    AuthenticationManager authenticationManager;
    private final CustomerService service;






    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @PostMapping("/signup")
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) {
        Customer addedCustomer = service.addCustomer(customer);
        return new ResponseEntity<>(addedCustomer, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginDTO loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        CustomerPrincipal customerDetails = (CustomerPrincipal) authentication.getPrincipal();
        List<String> roles = customerDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new CustomerDTO(jwt,
                customerDetails.getUsername(),
                roles));
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
        Optional<Customer> foundCustomer = service.findUserById(id);
        Customer updatedCustomer = service.updateCustomer(customer);
        if (foundCustomer.isEmpty()) {
            throw new NotFoundException("No data available of user ID: " + id);
        }
        return new ResponseEntity<>(updatedCustomer, HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteCustomer/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> removeCustomer(@PathVariable Long id) {
        Optional<Customer> foundCustomer = service.findUserById(id);
        if (foundCustomer.isEmpty()) {
            throw new NotFoundException("No data available of user ID: " + id);
        }
        service.removeCustomer(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}