package se.iths.projektarbetekomplexjava.service;

import org.springframework.stereotype.Service;
import se.iths.projektarbetekomplexjava.entity.Author;
import se.iths.projektarbetekomplexjava.entity.Book;
import se.iths.projektarbetekomplexjava.exception.BadRequestException;
import se.iths.projektarbetekomplexjava.exception.NotFoundException;
import se.iths.projektarbetekomplexjava.repository.AuthorRepository;
import se.iths.projektarbetekomplexjava.repository.BookRepository;
import se.iths.projektarbetekomplexjava.repository.PublisherRepository;
import se.iths.projektarbetekomplexjava.repository.ShoppingCartRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

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

        book.addAuthor(book.getAuthors().stream().findFirst().get());
        book.addPublisher(book.getPublisher());
        book.addToStock(book.getStock());
        return bookRepository.save(book);
    }

    public void removeBook(Long id) {
        bookRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        bookRepository.deleteById(id);
    }

//    public Book updateBook(Book book) {
//        Book foundBook = bookRepository.findById(book.getId()).orElseThrow(EntityNotFoundException::new);
//        return bookRepository.save(foundBook);
//    }

    public Book updateBook1(Book book) {
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

    public List<Book> getBooksByAuthor(String firstName, String lastName) {
        Author foundAuthor = authorRepository.findByFirstNameAndLastName(firstName, lastName);
        List<Book> booksByAuthor = bookRepository.findByAuthors(foundAuthor);
        if (booksByAuthor.isEmpty()) {
            throw new NotFoundException("No books by this author!");
        }
        return bookRepository.findByAuthors(foundAuthor);
    }

    public Iterable<Book> findAllBooks() {
        return bookRepository.findAll();
    }

//    public Book getBookByPublisingYear(String year) {
//
//    }
}