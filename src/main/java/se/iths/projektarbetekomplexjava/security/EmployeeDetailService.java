package se.iths.projektarbetekomplexjava.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import se.iths.projektarbetekomplexjava.entity.Employee;
/*
import se.iths.projektarbetekomplexjava.repository.EmployeeRepository;

import javax.transaction.Transactional;

@Service
public class EmployeeDetailService implements UserDetailsService {

    private final EmployeeRepository employeeRepository;

    public EmployeeDetailService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee = employeeRepository.findByUsername(username);
        if (employee == null){
            throw new UsernameNotFoundException("Can't find user with username: " + "username");
        }
        return new EmployeePrincipal(employee);
    }
}*/
