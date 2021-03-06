package se.iths.projektarbetekomplexjava.service;

import org.springframework.stereotype.Service;
import se.iths.projektarbetekomplexjava.entity.Author;
import se.iths.projektarbetekomplexjava.entity.Book;
import se.iths.projektarbetekomplexjava.entity.Publisher;
import se.iths.projektarbetekomplexjava.exception.BadRequestException;
import se.iths.projektarbetekomplexjava.exception.NotFoundException;
import se.iths.projektarbetekomplexjava.jms.Receiver;
import se.iths.projektarbetekomplexjava.jms.Sender;
import se.iths.projektarbetekomplexjava.repository.AuthorRepository;
import se.iths.projektarbetekomplexjava.repository.BookRepository;
import se.iths.projektarbetekomplexjava.repository.PublisherRepository;
import se.iths.projektarbetekomplexjava.repository.ShoppingCartRepository;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final PublisherRepository publisherRepository;
    private final ShoppingCartRepository shoppingCartRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository, PublisherRepository publisherRepository, ShoppingCartRepository shoppingCartRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.publisherRepository = publisherRepository;
        this.shoppingCartRepository = shoppingCartRepository;
    }

    public Book addBook(Book book) {
        Book foundBook = bookRepository.findByISBN13(book.getISBN13());

        if (!(foundBook == null)) {
            throw new BadRequestException("Book already exists in database!");
        }

        Publisher foundPublisher = publisherRepository.findByName(book.getPublisher().getName());
        if(foundPublisher ==null){
            Publisher publisher = book.getPublisher();
            publisherRepository.save(publisher);
            publisher.getBooks().add(book);
        }
        if(foundPublisher!=null){
            foundPublisher.getBooks().add(book);
        }
        Author foundAuthor = authorRepository.findByFirstNameAndLastName(book.getAuthors().stream().findFirst().get().getFirstName(), book.getAuthors().stream().findFirst().orElseThrow().getLastName());
        if(foundAuthor==null){
            Author author =book.getAuthors().stream().findFirst().get() ;
            authorRepository.save(author);
            author.getBooks().add(book);
        }
        if(foundAuthor!=null){
            foundAuthor.getBooks().add(book);
        }
        book.addToStock(book.getStock());
        try {
            Sender.sendItem(book.getISBN13(), book.getTitle(), book.getPublishingDate(), book.getWeight(),
                    book.getPages(), book.getLanguage(), book.getCategory(),book.getPrice());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookRepository.save(book);
    }

    public void removeBook(Long id) {
        bookRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        try{
            Receiver.receiveItem();
        }catch (Exception e){
            e.printStackTrace();
        }
        bookRepository.deleteById(id);
    }

    public Book updateBook(Book book) {
        Book foundBook = bookRepository.findById(book.getId()).orElseThrow(EntityNotFoundException::new);
        foundBook.setISBN13(book.getISBN13());
        foundBook.setTitle(book.getTitle());
        foundBook.setPublishingDate(book.getPublishingDate());
        foundBook.setWeight(book.getWeight());
        foundBook.setPages(book.getPages());
        foundBook.setLanguage(book.getLanguage());
        foundBook.setCategory(book.getCategory());
        foundBook.setPrice(book.getPrice());
        foundBook.setAuthors(book.getAuthors());
        foundBook.setPublisher(book.getPublisher());
        foundBook.setStock(book.getStock());
        foundBook.setBookToCart(book.getBookToCart());
        try {
            Sender.sendItem(book.getISBN13(), book.getTitle(), book.getPublishingDate(), book.getWeight(),
                    book.getPages(), book.getLanguage(), book.getCategory(), book.getPrice());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookRepository.save(book);
    }

    public Optional<Book> findByBookId(Long id) {
        return bookRepository.findById(id);
    }

    public List<Book> getBookByTitle(String title) {
        List<Book> foundBooks = bookRepository.findByTitle(title);
        if (foundBooks.isEmpty()) {
            throw new NotFoundException("Title  not found!");
        }
        return bookRepository.findByTitle(title);
    }

    public Book getBookByISBN13(String ISBN13) {
        Book foundBook = bookRepository.findByISBN13(ISBN13);
        if (foundBook == null) {
            throw new NotFoundException("Book not found!");
        }
        return bookRepository.findByISBN13(ISBN13);
    }

    public List<Book> getBooksByLanguage(String language) {
        List<Book> foundBooks = bookRepository.findByLanguage(language);
        if (foundBooks.isEmpty()) {
            throw new NotFoundException("Book not found!");
        }
        return bookRepository.findByLanguage(language);
    }

    public Set<Book> getBooksByAuthor(String firstName, String lastName) {
        Author foundAuthor = authorRepository.findByFirstNameAndLastName(firstName, lastName);
        if (foundAuthor == null) {
            throw new NotFoundException("Author not found!");
        }
        Set<Book> booksByAuthor =  foundAuthor.getBooks();
        System.out.println(booksByAuthor);
        if (booksByAuthor.isEmpty()) {
            throw new NotFoundException("No books by this author!");
        }
        return booksByAuthor;
    }

    public Iterable<Book> findAllBooks() {
        return bookRepository.findAll();
    }
}