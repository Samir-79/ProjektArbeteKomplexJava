package se.iths.projektarbetekomplexjava.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import se.iths.projektarbetekomplexjava.entity.Customer;
import se.iths.projektarbetekomplexjava.repository.CustomerRepository;

@Service
public class CustomerDetailService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    public CustomerDetailService(CustomerRepository customerRepository) {
        super();
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByUsername(username);
        if (customer == null){
            throw new UsernameNotFoundException("Can't find user with username: " + "username");
        }
        return new CustomerPrincipal(customer);
    }
}