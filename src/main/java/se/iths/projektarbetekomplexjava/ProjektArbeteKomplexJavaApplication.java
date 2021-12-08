package se.iths.projektarbetekomplexjava;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import se.iths.projektarbetekomplexjava.entity.*;
import se.iths.projektarbetekomplexjava.repository.*;

import java.text.SimpleDateFormat;

@SpringBootApplication
public class ProjektArbeteKomplexJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjektArbeteKomplexJavaApplication.class, args);
    }

   /* @Bean
    public CommandLineRunner setUpRole(RoleRepository roleRepository) {
        return (args) -> {
            roleRepository.save(new Role("ADMIN"));
            roleRepository.save(new Role("USER"));
        };
    }*/

/*    @Bean
    CommandLineRunner setUpCustomer(CustomerRepository customerRepository) {
        return (args) -> {
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

    @Bean
    CommandLineRunner setUpEmployee(EmployeeRepository employeeRepository) {
        return (args) -> {
            employeeRepository.save(new Employee("Philip", "", "", "", "", "", ""));
            employeeRepository.save(new Employee("Samir", "", "", "", "", "", ""));
            employeeRepository.save(new Employee("Biniam", "", "", "", "", "", ""));
        };
    }*/

    @Bean
    public CommandLineRunner setUpShoppingCart(ShoppingCartRepository shoppingCartRepository) {
        return (args) -> {
            shoppingCartRepository.save(new ShoppingCart(450.00,2));
            shoppingCartRepository.save(new ShoppingCart(300.00,1));
            shoppingCartRepository.save(new ShoppingCart(250.00,6));
        };
    }

    @Bean
    public CommandLineRunner setUpOrder(OrderRepository orderRepository) {
        return (args) -> {
            //new Order(new SimpleDateFormat("yyyy-MM-dd").parse("2021-12-07")
            orderRepository.save(new Order("2021-12-07","motorcycle","styrbjörnsvägen",5000.00));
            orderRepository.save(new Order("2021-11-11","Car","styrbjörnsvägen",1200.00));
            orderRepository.save(new Order("2021-10-10","plane","styrbjörnsvägen",245.50));
        };
    }

    @Bean
    public CommandLineRunner setUpPayment(PaymentRepository paymentRepository) {
        return (args) -> {
            paymentRepository.save(new Payment("Swedbank","1233...",06,2025,345,"biniam"));
            paymentRepository.save(new Payment("SEB","1233...",10,2026,123,"Philip"));
            paymentRepository.save(new Payment("Nordia","1233...",12,2028,678,"Samir"));
        };
    }
}
