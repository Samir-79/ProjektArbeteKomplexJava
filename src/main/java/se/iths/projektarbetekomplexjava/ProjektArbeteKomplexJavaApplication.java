package se.iths.projektarbetekomplexjava;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import se.iths.projektarbetekomplexjava.entity.*;
import se.iths.projektarbetekomplexjava.repository.*;

import java.util.Arrays;

@SpringBootApplication
public class ProjektArbeteKomplexJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjektArbeteKomplexJavaApplication.class, args);
    }

    @Bean
    public CommandLineRunner setUpSampleDataBaseGenerator(CustomerRepository customerRepository, EmployeeRepository employeeRepository,
                                                          BookRepository bookRepository,
                                                          AuthorRepository authorRepository, PublisherRepository publisherRepository,
                                                          StockRepository stockRepository, ShoppingCartRepository shoppingCartRepository,
                                                          OrderRepository orderRepository, PaymentRepository paymentRepository) {
        return (args) -> {
            Customer customer1 = new Customer("Sara", "Hamilton", "Street 69", "53513515465", "SAHL", "sara.hamilton@", "sdf4sd7f48", Role.USER);
            Customer customer2 = new Customer("Ed", "Thomas", "Sector 8", "54646516557", "EDMA", "ed.thomas@", "ssdfhrsghs", Role.USER);
            Customer customer3 = new Customer("Jason", "Voorhees", "Crystal Lake Camp", "8784564984", "JAES", "jason.voorhees@", "13547844", Role.USER);
            Customer customer4 = new Customer("John", "Smith", "Street 78", "4987531357", "JOSI", "john.smith@", "sdfhdfhdssd", Role.USER);
            Customer customer5 = new Customer("Robert", "Gustafsson", "Vallhallavägen 45", "213548654", "ROSO", "robert.gustafsson@", "sdfsdfrghdf", Role.USER);
            Customer customer6 = new Customer("Eva", "Andersson", "Vasa Gatan 64", "879845143", "EVSO", "eva.andersson@", "öjiohjmghj", Role.USER);
            Customer customer7 = new Customer("Mathilda", "Lindqvist", "Oden Gatan 25", "658424896", "MALI", "mathilda.lindqvist@", "ghjfhdfhg", Role.USER);
            Customer customer8 = new Customer("Albert", "Wesker", "Street 23", "1654746835", "ALWR", "albert.wesker@umbrella.com", "dfggkghjfg", Role.USER);
            Customer customer9 = new Customer("Lisa", "Lavinski", "Sector 4", "351687146547", "LILI", "lisa.lavinski@", "asdsefrsdf", Role.USER);
            Customer customer10 = new Customer("Veronika", "Jones", "Sector 2", "56498765684", "VEJS", "veronika.jones@", "sdfsgtfgthdg", Role.USER);

            customerRepository.saveAll(Arrays.asList(customer1, customer2, customer3, customer4, customer5, customer6, customer7, customer8, customer9, customer10));


            Employee employee1 = new Employee("Philip", "Mattsson", "Classified", "Classified", "PHMA", "philip.mattsson@mail.com", "sdgfghdfsfg",Role.ADMIN);
            Employee employee2 = new Employee("Samir", "Alsalhani", "Classified", "Classified", "SAAL", "samir.alsalhani@mail.com", "fghfghdfhk",Role.ADMIN);
            Employee employee3 = new Employee("Biniam", "Haile Kifle", "Classified", "Classified", "BIHK", "biniam.haile.kifle@mail.com", "hgkfghgdff",Role.ADMIN);

            employeeRepository.saveAll(Arrays.asList(employee1, employee2, employee3));

//            Role role1 = new Role("USER");
//            Role role2 = new Role("ADMIN");
            Role role1 = Role.ADMIN;
            Role role2 = Role.USER;


            //roleRepository.saveAll(Arrays.asList(role1, role2));

//            role1.addCustomer(customer1);
//            role1.addCustomer(customer2);
//            role1.addCustomer(customer3);
//            role1.addCustomer(customer4);
//            role1.addCustomer(customer5);
//            role1.addCustomer(customer6);
//            role1.addCustomer(customer7);
//            role1.addCustomer(customer8);
//            role1.addCustomer(customer9);
//            role1.addCustomer(customer10);
//
//            role2.addEmployee(employee1);
//            role2.addEmployee(employee2);
//            role2.addEmployee(employee3);
//
//
//            roleRepository.saveAll(Arrays.asList(role1, role2));


            employeeRepository.saveAll(Arrays.asList(employee1, employee2, employee3));
            //DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

            Book book1 = new Book("9789178871148", "Depphjärnan : Varför mår vi så dåligt när vi har det så bra?", "2021-10-28", 629L, 224, "Hälso&Livsstil", "Svenska", 250L);
            Book book2 = new Book("9789178872718", "Middag i en gryta", "2021-11-03", 734L, 160, "Kokböcker", "Svenska", 251L);
            Book book3 = new Book("9789127164031", "Nattkorpen", "2021-01-15", 318L, 183, "Barnböcker", "Svenska", 252L);
            Book book4 = new Book("9789100181406 ", "Tim:Biografin om Avicii", "2021-11-16", 606L, 330, "Biografier", "Svenska", 253L);
            Book book5 = new Book("9789100187989", "Löpa varg", "2021-08-24", 366L, 150, "Albert Bonniers Förlag", "Svenska", 254L);
            Book book6 = new Book("9789100197186", "Paradiset", "2021-12-11", 383L, 276, "Skönlitteratur", "Svenska", 255L);
            Book book7 = new Book("9781565841635", "Paradise", "1995-05-18", null, 246, "Skönlitteratur", "Engelska", 256L);
            Book book8 = new Book("9781292273730", "Java How to Program, Late Objects,Global Edition", "2019-09-11", null, 1248, "Programmering", "Engelska", 257L);

            bookRepository.saveAll(Arrays.asList(book1, book2, book3, book4, book5, book6, book7, book8));

            Author author1 = new Author("Anders", "Hansen");
            Author author2 = new Author("Tareq", "Taylor");
            Author author3 = new Author("Johan", "Rundberg");
            Author author4 = new Author("Måns", "Mosesson");
            Author author5 = new Author("Kerstin", "Ekman");
            Author author6 = new Author("Abdulrazak", "Gurnah");
            Author author7 = new Author("Harvey", "Deitel");
            Author author8 = new Author("Paul", "Deitel");

            authorRepository.saveAll(Arrays.asList(author1, author2, author3, author4, author5, author6, author7, author8));

            Publisher publisher1 = new Publisher("Bonnier Fakta");
            Publisher publisher2 = new Publisher(" Natur Kultur Allmänlitteratur");
            Publisher publisher3 = new Publisher(" Albert Bonniers Förlag");
            Publisher publisher4 = new Publisher("The New Press");
            Publisher publisher5 = new Publisher("Pearson Education Limited");

            publisherRepository.saveAll(Arrays.asList(publisher1, publisher2, publisher3, publisher4, publisher5));

            Stock stock1 = new Stock(100, true);
            Stock stock2 = new Stock(0, false);
            Stock stock3 = new Stock(500, true);
            Stock stock4 = new Stock(200, true);
            Stock stock5 = new Stock(300, true);
            Stock stock6 = new Stock(50, true);
            Stock stock7 = new Stock(0, false);
            Stock stock8 = new Stock(10, true);

            stockRepository.saveAll(Arrays.asList(stock1, stock2, stock3, stock4, stock5, stock6, stock7, stock8));

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

            bookRepository.saveAll(Arrays.asList(book1, book2, book3, book4, book5, book6, book7, book8));
            authorRepository.saveAll(Arrays.asList(author1, author2, author3, author4, author5, author6, author7, author8));
            publisherRepository.saveAll(Arrays.asList(publisher1, publisher2, publisher3, publisher4, publisher5));
            stockRepository.saveAll(Arrays.asList(stock1, stock2, stock3, stock4, stock5, stock6, stock7, stock8));

            ShoppingCart shoppingCart1 = new ShoppingCart(450.00, 2);
            ShoppingCart shoppingCart2 = new ShoppingCart(300.00, 1);
            ShoppingCart shoppingCart3 = new ShoppingCart(250.00, 6);

            shoppingCartRepository.saveAll(Arrays.asList(shoppingCart1, shoppingCart2, shoppingCart3));

            Orders order1 = new Orders("2021-12-07", "motorcycle", "styrbjörnsvägen", 5000.00);
            Orders order2 = new Orders("2021-11-11", "Car", "styrbjörnsvägen", 1200.00);
            Orders order3 = new Orders("2021-10-10", "plane", "styrbjörnsvägen", 245.50);

            orderRepository.saveAll(Arrays.asList(order1, order2, order3));

            Payment payment1 = new Payment("Swedbank", "1233...", 6, 2025, 345, "biniam");
            Payment payment2 = new Payment("SEB", "1233...", 10, 2026, 123, "Philip");
            Payment payment3 = new Payment("Nordia", "1233...", 12, 2028, 678, "Samir");

            paymentRepository.saveAll(Arrays.asList(payment1, payment2, payment3));

            shoppingCart1.addCustomer(customer1);
            shoppingCart2.addCustomer(customer2);
            shoppingCart3.addCustomer(customer3);

            shoppingCart1.addBook(book1);
            shoppingCart1.addBook(book2);
            shoppingCart1.addBook(book3);
            shoppingCart1.addOrder(order1);


            shoppingCartRepository.save(shoppingCart1);
            shoppingCartRepository.save(shoppingCart2);
            shoppingCartRepository.save(shoppingCart3);

            customerRepository.saveAll(Arrays.asList(customer1, customer2, customer3, customer4, customer5, customer6, customer7, customer8, customer9, customer10));

        };
    }
}