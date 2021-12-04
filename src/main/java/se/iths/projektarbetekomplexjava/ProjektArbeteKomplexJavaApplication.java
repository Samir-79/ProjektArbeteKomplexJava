package se.iths.projektarbetekomplexjava;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import se.iths.projektarbetekomplexjava.entity.*;
import se.iths.projektarbetekomplexjava.repository.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Currency;
import java.util.Date;

@SpringBootApplication
public class ProjektArbeteKomplexJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjektArbeteKomplexJavaApplication.class, args);
    }

    @Bean
    public CommandLineRunner setUpRole(RoleRepository roleRepository) {
        return (args) -> {
            roleRepository.save(new Role("ADMIN"));
            roleRepository.save(new Role("USER"));
        };
    }

    @Bean
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
    }

    @Bean
    CommandLineRunner setUpBook(BookRepository bookRepository) {
        return (args) -> {
            bookRepository.save(new Book("9789178871148", "Depphjärnan : Varför mår vi så dåligt när vi har det så bra?", DateFormat.getDateInstance().parse("2021-10-28"), 629L, 224, "Hälso&Livsstil", "Svenska", 250L));
            bookRepository.save(new Book("9789178872718", "Middag i en gryta", DateFormat.getDateInstance().parse("2021-11-03"), 734L, 160, "Kokböcker", "Svenska", 251L));
            bookRepository.save(new Book("9789127164031", "Nattkorpen", DateFormat.getDateInstance().parse("2021-01-15"), 318L, 183, "Barnböcker", "Svenska", 252L));
            bookRepository.save(new Book("9789100181406 ", "Tim:Biografin om Avicii", DateFormat.getDateInstance().parse("2021-11-16"), 606L, 330, "Biografier", "Svenska", 253L));
            bookRepository.save(new Book("9789100187989", "Löpa varg", DateFormat.getDateInstance().parse("2021-08-24"), 366L, 150, "Albert Bonniers Förlag", "Svenska", 254L));
            bookRepository.save(new Book("9789100197186", "Paradiset", DateFormat.getDateInstance().parse("2021-12-11"), 383L, 276, "Skönlitteratur", "Svenska", 255L));
            bookRepository.save(new Book("9781565841635", "Paradise", DateFormat.getDateInstance().parse("1995-05-18"), null, 246, "Skönlitteratur", "Engelska", 256L));
            bookRepository.save(new Book("9781292273730", "Java How to Program, Late Objects,Global Edition", DateFormat.getDateInstance().parse("2019-09-11"), null, 1248, "Programmering", "Engelska", 257L));
        };
    }

    @Bean
    CommandLineRunner setUpAuthor(AuthorRespository authorRespository) {
        return (args) -> {
            authorRespository.save(new Author("Anders", "Hansen"));
            authorRespository.save(new Author("Tareq", "Taylor"));
            authorRespository.save(new Author("Johan", "Rundberg"));
            authorRespository.save(new Author("Måns", "Mosesson"));
            authorRespository.save(new Author("Kerstin", "Ekman"));
            authorRespository.save(new Author("Abdulrazak", "Gurnah"));
            authorRespository.save(new Author("Harvey", "Deitel"));
            authorRespository.save(new Author("Paul", "Deitel"));

        };
    }

    @Bean
    CommandLineRunner setUpPublisher(PublisherRepository publisherRepository) {
        return (args) -> {
            publisherRepository.save(new Publisher("Bonnier Fakta"));
            publisherRepository.save(new Publisher(" Natur Kultur Allmänlitteratur"));
            publisherRepository.save(new Publisher(" Albert Bonniers Förlag"));
            publisherRepository.save(new Publisher("The New Press"));
            publisherRepository.save(new Publisher("Pearson Education Limited"));

        };
    }

    @Bean
    CommandLineRunner setUpStock(StockRepository stockRepository) {
        return (args) -> {
            stockRepository.save(new Stock(100, true));
            stockRepository.save(new Stock(0, false));
            stockRepository.save(new Stock(500, true));
            stockRepository.save(new Stock(200, true));
            stockRepository.save(new Stock(300, true));
            stockRepository.save(new Stock(50, true));
            stockRepository.save(new Stock(0, false));
            stockRepository.save(new Stock(10, true));
        };
    }


}
