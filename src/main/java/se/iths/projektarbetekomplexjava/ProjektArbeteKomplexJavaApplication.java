package se.iths.projektarbetekomplexjava;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import se.iths.projektarbetekomplexjava.entity.Author;
import se.iths.projektarbetekomplexjava.entity.Book;
import se.iths.projektarbetekomplexjava.entity.Publisher;
import se.iths.projektarbetekomplexjava.entity.Stock;
import se.iths.projektarbetekomplexjava.repository.*;

import java.time.LocalDate;
import java.util.Arrays;


@SpringBootApplication
public class ProjektArbeteKomplexJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjektArbeteKomplexJavaApplication.class, args);
    }


    @Bean
    public CommandLineRunner setUpSampleDataBaseGenerator(
            BookRepository bookRepository,
            AuthorRepository authorRepository, PublisherRepository publisherRepository,
            StockRepository stockRepository
    ) {
        return (args) -> {


            Book book1 = new Book("9789178871148", "Depphjärnan : Varför mår vi så dåligt när vi har det så bra?", LocalDate.parse("2021-10-28"), 629L, 224, "Svenska", "Hälso&Livsstil", 250.0  );
            Book book2 = new Book("9789178872718", "Middag i en gryta", LocalDate.parse("2021-11-03"), 734L, 160, "Svenska", "Kokböcker", 251.0);
            Book book3 = new Book("9789127164031", "Nattkorpen", LocalDate.parse("2021-01-15"), 318L, 183, "Svenska", "Barnböcker", 252.0);
            Book book4 = new Book("9789100181406 ", "Tim:Biografin om Avicii", LocalDate.parse("2021-11-16"), 606L, 330, "Svenska", "Biografier", 253.0);
            Book book5 = new Book("9789100187989", "Löpa varg", LocalDate.parse("2021-08-24"), 366L, 150, "Svenska", "Albert Bonniers Förlag", 254.0);
            Book book6 = new Book("9789100197186", "Paradiset", LocalDate.parse("2021-12-11"), 383L, 276, "Svenska", "Skönlitteratur", 255.0);
            Book book7 = new Book("9781565841635", "Paradise", LocalDate.parse("1995-05-18"), null, 246, "Engelska", "Skönlitteratur", 256.0);
            Book book8 = new Book("9781292273730", "Java How to Program, Late Objects,Global Edition", LocalDate.parse("2019-09-11"), null, 1248, "Engelska", "Programmering", 257.0);

            Author author1 = new Author("Anders", "Hansen");
            Author author2 = new Author("Tareq", "Taylor");
            Author author3 = new Author("Johan", "Rundberg");
            Author author4 = new Author("Måns", "Mosesson");
            Author author5 = new Author("Kerstin", "Ekman");
            Author author6 = new Author("Abdulrazak", "Gurnah");
            Author author7 = new Author("Harvey", "Deitel");
            Author author8 = new Author("Paul", "Deitel");

            //authorRepository.saveAll(Arrays.asList(author1, author2, author3, author4, author5, author6, author7, author8));

            Publisher publisher1 = new Publisher("Bonnier Fakta");
            Publisher publisher2 = new Publisher(" Natur Kultur Allmänlitteratur");
            Publisher publisher3 = new Publisher(" Albert Bonniers Förlag");
            Publisher publisher4 = new Publisher("The New Press");
            Publisher publisher5 = new Publisher("Pearson Education Limited");

            Stock stock1 = new Stock(100, true);

            Stock stock2 = new Stock(100, false);
            Stock stock3 = new Stock(100, true);
            Stock stock4 = new Stock(100, true);
            Stock stock5 = new Stock(100, true);
            Stock stock6 = new Stock(100, true);
            Stock stock7 = new Stock(100, false);
            Stock stock8 = new Stock(100, true);


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
        };
    }
}