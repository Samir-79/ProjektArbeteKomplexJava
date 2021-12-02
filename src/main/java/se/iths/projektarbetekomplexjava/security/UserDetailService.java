package se.iths.projektarbetekomplexjava.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import se.iths.projektarbetekomplexjava.entity.CustomerEntity;
import se.iths.projektarbetekomplexjava.repository.CustomerRepository;

@Service
public class UserDetailService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    public UserDetailService(CustomerRepository customerRepository) {
        super();
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CustomerEntity entity = customerRepository.findByUsername(username);
        if (entity == null){
            throw new UsernameNotFoundException("Can't find user with username: " + "username");
        }
        return new se.iths.projektarbetekomplexjava.security.UserPrincipal(entity);
    }
}
