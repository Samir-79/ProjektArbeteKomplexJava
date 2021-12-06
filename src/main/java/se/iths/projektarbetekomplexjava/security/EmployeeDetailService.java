package se.iths.projektarbetekomplexjava.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import se.iths.projektarbetekomplexjava.entity.Employee;
import se.iths.projektarbetekomplexjava.repository.EmployeeRepository;

@Service
public class EmployeeDetailService implements UserDetailsService {

    private final EmployeeRepository employeeRepository;

    public EmployeeDetailService(EmployeeRepository employeeRepository) {
        super();
        this.employeeRepository = employeeRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee entity = employeeRepository.findByUsername(username);
        if (entity == null){
            throw new UsernameNotFoundException("Can't find user with username: " + "username");
        }
        return new EmployeePrincipal(entity);
    }
}
