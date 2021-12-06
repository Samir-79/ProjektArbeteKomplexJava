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
import java.util.Locale;

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
    CommandLineRunner setUpBookAuthorPublisherStockAndConnect(BookRepository bookRepository,AuthorRespository authorRespository,PublisherRepository publisherRepository,StockRepository stockRepository) {
        return (args) -> {
            //DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);


            Book book1 = new Book("9789178871148", "Depphjärnan : Varför mår vi så dåligt när vi har det så bra?", "2021-10-28", 629L, 224, "Hälso&Livsstil", "Svenska", 250L);
            Book book2 = new Book("9789178872718", "Middag i en gryta", "2021-11-03", 734L, 160, "Kokböcker", "Svenska", 251L);
            Book book3 = new Book("9789127164031", "Nattkorpen", "2021-01-15", 318L, 183, "Barnböcker", "Svenska", 252L);
            Book book4 = new Book("9789100181406 ", "Tim:Biografin om Avicii", "2021-11-16", 606L, 330, "Biografier", "Svenska", 253L);
            Book book5 = new Book("9789100187989", "Löpa varg", "2021-08-24", 366L, 150, "Albert Bonniers Förlag", "Svenska", 254L);
            Book book6 = new Book("9789100197186", "Paradiset","2021-12-11", 383L, 276, "Skönlitteratur", "Svenska", 255L);
            Book book7 = new Book("9781565841635", "Paradise", "1995-05-18", null, 246, "Skönlitteratur", "Engelska", 256L);
            Book book8 = new Book("9781292273730", "Java How to Program, Late Objects,Global Edition", "2019-09-11", null, 1248, "Programmering", "Engelska", 257L);


            bookRepository.save(book1);
            bookRepository.save(book2);
            bookRepository.save(book3);
            bookRepository.save(book4);
            bookRepository.save(book5);
            bookRepository.save(book6);
            bookRepository.save(book7);
            bookRepository.save(book8);

            Author author1 = new Author("Anders", "Hansen");
            Author author2 = new Author("Tareq", "Taylor");
            Author author3 = new Author("Johan", "Rundberg");
            Author author4 = new Author("Måns", "Mosesson");
            Author author5 = new Author("Kerstin", "Ekman");
            Author author6 = new Author("Abdulrazak", "Gurnah");
            Author author7 = new Author("Harvey", "Deitel");
            Author author8 = new Author("Paul", "Deitel");

            authorRespository.save(author1);
            authorRespository.save(author2);
            authorRespository.save(author3);
            authorRespository.save(author4);
            authorRespository.save(author5);
            authorRespository.save(author6);
            authorRespository.save(author7);
            authorRespository.save(author8);


            Publisher publisher1 = new Publisher("Bonnier Fakta");
            Publisher publisher2 = new Publisher(" Natur Kultur Allmänlitteratur");
            Publisher publisher3 = new Publisher(" Albert Bonniers Förlag");
            Publisher publisher4 = new Publisher("The New Press");
            Publisher publisher5 = new Publisher("Pearson Education Limited");

            publisherRepository.save(publisher1);
            publisherRepository.save(publisher2);
            publisherRepository.save(publisher3);
            publisherRepository.save(publisher4);
            publisherRepository.save(publisher5);

            Stock stock1 = new Stock(100, true);
            Stock stock2 = new Stock(0, false);
            Stock stock3 = new Stock(500, true);
            Stock stock4 = new Stock(200, true);
            Stock stock5 = new Stock(300, true);
            Stock stock6 = new Stock(50, true);
            Stock stock7 = new Stock(0, false);
            Stock stock8 = new Stock(10, true);

            stockRepository.save(stock1);
            stockRepository.save(stock2);
            stockRepository.save(stock3);
            stockRepository.save(stock4);
            stockRepository.save(stock5);
            stockRepository.save(stock6);
            stockRepository.save(stock7);
            stockRepository.save(stock8);


            book1.addAuthor(author1);
            book1.addPublisher(publisher1);
            book1.addToStock(stock1);

            book2.addAuthor(author2);
            book2.addPublisher(publisher1);
            book2.addToStock(stock2);

            book3.addAuthor(author2);
            book3.addPublisher(publisher2);
            book3.addToStock(stock3);

            book4.addAuthor(author4);
            book4.addPublisher(publisher3);
            book4.addToStock(stock4);

            book5.addAuthor(author5);
            book5.addPublisher(publisher3);
            book5.addToStock(stock5);

            book6.addAuthor(author6);
            book6.addPublisher(publisher3);
            book6.addToStock(stock6);

            book7.addAuthor(author6);
            book7.addPublisher(publisher4);
            book7.addToStock(stock7);

            book8.addAuthor(author7);
            book8.addAuthor(author8);
            book8.addPublisher(publisher5);
            book8.addToStock(stock8);

            bookRepository.save(book1);
            bookRepository.save(book2);
            bookRepository.save(book3);
            bookRepository.save(book4);
            bookRepository.save(book5);
            bookRepository.save(book6);
            bookRepository.save(book7);
            bookRepository.save(book8);

            authorRespository.save(author1);
            authorRespository.save(author2);
            authorRespository.save(author3);
            authorRespository.save(author4);
            authorRespository.save(author5);
            authorRespository.save(author6);
            authorRespository.save(author7);
            authorRespository.save(author8);

            publisherRepository.save(publisher1);
            publisherRepository.save(publisher2);
            publisherRepository.save(publisher3);
            publisherRepository.save(publisher4);
            publisherRepository.save(publisher5);

            stockRepository.save(stock1);
            stockRepository.save(stock2);
            stockRepository.save(stock3);
            stockRepository.save(stock4);
            stockRepository.save(stock5);
            stockRepository.save(stock6);
            stockRepository.save(stock7);
            stockRepository.save(stock8);



        };
    }

//    @Bean
//    CommandLineRunner setUpAuthor(AuthorRespository authorRespository) {
//        return (args) -> {
//
//        };
//    }
//
//    @Bean
//    CommandLineRunner setUpPublisher(PublisherRepository publisherRepository) {
//        return (args) -> {
//
//
//        };
//    }
//
//    @Bean
//    CommandLineRunner setUpStock(StockRepository stockRepository) {
//        return (args) -> {
//
//        };
//    }
//
//    @Bean
//    CommandLineRunner connectBookAuthorPublisherStockEntities(BookRepository bookRepository) {
//        return (args) ->{
//
//
//        };
//    }


}
