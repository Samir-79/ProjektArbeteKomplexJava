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

import java.util.Arrays;

@SpringBootApplication
public class ProjektArbeteKomplexJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjektArbeteKomplexJavaApplication.class, args);
    }

    @Bean
    public CommandLineRunner setUpCustomerEmployeeAndRole(CustomerRepository customerRepository, EmployeeRepository employeeRepository, RoleRepository roleRepository) {
        return (args) -> {
            Customer customer1 = new Customer("Sara", "Hamilton", "Street 69", "53513515465", "SAHL", "sara.hamilton@", "sdf4sd7f48");
            Customer customer2 = new Customer("Ed", "Thomas", "Sector 8", "54646516557", "EDMA", "ed.thomas@", "ssdfhrsghs");
            Customer customer3 = new Customer("Jason", "Voorhees", "Crystal Lake Camp", "8784564984", "JAES", "jason.voorhees@", "13547844");
            Customer customer4 = new Customer("John", "Smith", "Street 78", "4987531357", "JOSI", "john.smith@", "sdfhdfhdssd");
            Customer customer5 = new Customer("Robert", "Gustafsson", "Vallhallavägen 45", "213548654", "ROSO", "robert.gustafsson@", "sdfsdfrghdf");
            Customer customer6 = new Customer("Eva", "Andersson", "Vasa Gatan 64", "879845143", "EVSO", "eva.andersson@", "öjiohjmghj");
            Customer customer7 = new Customer("Mathilda", "Lindqvist", "Oden Gatan 25", "658424896", "MALI", "mathilda.lindqvist@", "ghjfhdfhg");
            Customer customer8 = new Customer("Albert", "Wesker", "Street 23", "1654746835", "ALWR", "albert.wesker@umbrella.com", "dfggkghjfg");
            Customer customer9 = new Customer("Lisa", "Lavinski", "Sector 4", "351687146547", "LILI", "lisa.lavinski@", "asdsefrsdf");
            Customer customer10 = new Customer("Veronika", "Jones", "Sector 2", "56498765684", "VEJS", "veronika.jones@", "sdfsgtfgthdg");

            customerRepository.save(customer1);
            customerRepository.save(customer2);
            customerRepository.save(customer3);
            customerRepository.save(customer4);
            customerRepository.save(customer5);
            customerRepository.save(customer6);
            customerRepository.save(customer7);
            customerRepository.save(customer8);
            customerRepository.save(customer9);
            customerRepository.save(customer10);

            Employee employee1 = new Employee("Philip", "Mattsson", "Classified", "Classified", "PHMA", "philip.mattsson@mail.com", "sdgfghdfsfg");
            Employee employee2 = new Employee("Samir", "Alsalhani", "Classified", "Classified", "SAAL", "samir.alsalhani@mail.com", "fghfghdfhk");
            Employee employee3 = new Employee("Biniam", "Haile Kifle", "Classified", "Classified", "BIHK", "biniam.haile.kifle@mail.com", "hgkfghgdff");

            employeeRepository.save(employee1);
            employeeRepository.save(employee2);
            employeeRepository.save(employee3);

            Role role1 = new Role("USER");
            Role role2 = new Role("ADMIN");

            roleRepository.save(role1);
            roleRepository.save(role2);

            role1.addCustomer(customer1);
            role1.addCustomer(customer2);
            role1.addCustomer(customer3);
            role1.addCustomer(customer4);
            role1.addCustomer(customer5);
            role1.addCustomer(customer6);
            role1.addCustomer(customer7);
            role1.addCustomer(customer8);
            role1.addCustomer(customer9);
            role1.addCustomer(customer10);

            role2.addEmployee(employee1);
            role2.addEmployee(employee2);
            role2.addEmployee(employee3);

            customerRepository.save(customer1);
            customerRepository.save(customer2);
            customerRepository.save(customer3);
            customerRepository.save(customer4);
            customerRepository.save(customer5);
            customerRepository.save(customer6);
            customerRepository.save(customer7);
            customerRepository.save(customer8);
            customerRepository.save(customer9);
            customerRepository.save(customer10);

            employeeRepository.save(employee1);
            employeeRepository.save(employee2);
            employeeRepository.save(employee3);

            roleRepository.save(role1);
            roleRepository.save(role2);
        };
    }
}
