package se.iths.projektarbetekomplexjava;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import se.iths.projektarbetekomplexjava.entity.Customer;
import se.iths.projektarbetekomplexjava.entity.Employee;
import se.iths.projektarbetekomplexjava.entity.Role;
import se.iths.projektarbetekomplexjava.repository.CustomerRepository;
import se.iths.projektarbetekomplexjava.repository.EmployeeRepository;
import se.iths.projektarbetekomplexjava.repository.RoleRepository;

@SpringBootApplication
public class ProjektArbeteKomplexJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjektArbeteKomplexJavaApplication.class, args);
    }

    @Bean
    public CommandLineRunner setUpRole(RoleRepository roleRepository){
        return (args) -> {
            roleRepository.save(new Role("ADMIN"));
            roleRepository.save(new Role("USER"));
        };
    }

    @Bean CommandLineRunner setUpCustomer(CustomerRepository customerRepository){
        return  (args) -> {
            customerRepository.save(new Customer("Sara", "Hamilton", "", "", "", "", ""));
            customerRepository.save(new Customer("Ed", "Thomas", "", "", "", "", ""));
            customerRepository.save(new Customer("Jason", "Voorhees", "", "", "", "", ""));
            customerRepository.save(new Customer("John", "Smith", "", "", "", "", ""));
            customerRepository.save(new Customer("Robert", "", "", "", "", "", ""));
            customerRepository.save(new Customer("Eva", "", "", "", "", "", ""));
            customerRepository.save(new Customer("Mathilda", "", "", "", "", "", ""));
            customerRepository.save(new Customer("Albert", "", "", "", "", "", ""));
            customerRepository.save(new Customer("Lisa", "", "", "", "", "", ""));
            customerRepository.save(new Customer("Veronika", "", "", "", "", "", ""));
        };
    }

    @Bean CommandLineRunner setUpEmployee(EmployeeRepository employeeRepository){
        return  (args) -> {
            employeeRepository.save(new Employee("Philip", "", "", "", "", "", ""));
            employeeRepository.save(new Employee("Samir", "", "", "", "", "", ""));
            employeeRepository.save(new Employee("Biniam", "", "", "", "", "", ""));
        };
    }
}
