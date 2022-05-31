//package se.iths.projektarbetekomplexjava;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import org.springframework.boot.context.event.ApplicationReadyEvent;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.event.EventListener;
//import se.iths.projektarbetekomplexjava.entity.*;
//import se.iths.projektarbetekomplexjava.repository.*;
//
//import java.time.LocalDate;
//import java.util.*;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//
//@AllArgsConstructor
//@Configuration
//@Data
//public class BookDB {
//
//    private BookRepository bookRepository ;
//    private static AuthorRepository authorRepository;
//    private static PublisherRepository publisherRepository;
//    private static StockRepository stockRepository;
//
//
//
//
//    @EventListener(ApplicationReadyEvent.class)
//    public void addBooksToDatabase() {
//        bookRepository.saveAll(createBookDB());
//
//    }
//
//    private static List<Book> createBookDB() {
//        List<Book> booksDB = new ArrayList<>();
//        //Book book1 = new Book("9789178871148", "Depphjärnan : Varför mår vi så dåligt när vi har det så bra?", LocalDate.parse("2021-10-28"), 629L, 224, "Svenska", "Hälso&Livsstil", 250L, new HashSet<>(List.of(new Author("Anders", "Hansen"))),new Publisher("Bonnier Fakta"),new Stock(500,true));
//
//        Book book1 = new Book("9789178871148", "Depphjärnan : Varför mår vi så dåligt när vi har det så bra?", LocalDate.parse("2021-10-28"), 629L, 224, "Svenska", "Hälso&Livsstil", 250L);
////        Book book2 = new Book("9789178872718", "Middag i en gryta", LocalDate.parse("2021-11-03"), 734L, 160, "Svenska", "Kokböcker", 251L);
////        Book book3 = new Book("9789127164031", "Nattkorpen", LocalDate.parse("2021-01-15"), 318L, 183, "Svenska", "Barnböcker", 252L);
////        Book book4 = new Book("9789100181406 ", "Tim:Biografin om Avicii", LocalDate.parse("2021-11-16"), 606L, 330, "Svenska", "Biografier", 253L);
////        Book book5 = new Book("9789100187989", "Löpa varg", LocalDate.parse("2021-08-24"), 366L, 150, "Svenska", "Albert Bonniers Förlag", 254L);
////        Book book6 = new Book("9789100197186", "Paradiset", LocalDate.parse("2021-12-11"), 383L, 276, "Svenska", "Skönlitteratur", 255L);
////        Book book7 = new Book("9781565841635", "Paradise", LocalDate.parse("1995-05-18"), null, 246, "Engelska", "Skönlitteratur", 256L);
////        Book book8 = new Book("9781292273730", "Java How to Program, Late Objects,Global Edition", LocalDate.parse("2019-09-11"), null, 1248, "Engelska", "Programmering", 257L);
//
//
//
//        Author author1 = new Author("Anders","Hansen");
//
//        Publisher publisher1 = new Publisher("Bonnier Fakta");
//        Stock stock1 = new Stock(100, true);
//
//
//        book1.addAuthor(author1);
//
//        book1.addPublisher(publisher1);
//        book1.addToStock(stock1);
//
//
//
//
//        authorRepository.saveAll(List.of(author1));
//        publisherRepository.saveAll(Arrays.asList(publisher1));
//        stockRepository.saveAll(Arrays.asList(stock1));
//        booksDB.add(book1);
//        return booksDB;
//    }
//}