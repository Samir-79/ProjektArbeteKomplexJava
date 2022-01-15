package se.iths.projektarbetekomplexjava.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import se.iths.projektarbetekomplexjava.entity.Book;
import se.iths.projektarbetekomplexjava.exception.NotFoundException;
import se.iths.projektarbetekomplexjava.service.BookService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("bokhandel/api/v1/book")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/addbook")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        Book addedBook = bookService.addBook(book);
        return new ResponseEntity<>(addedBook, HttpStatus.CREATED);
    }

    @GetMapping("/getbookbyisbn/{ISBN13}")
    public ResponseEntity<Book> getByISBN(@PathVariable String ISBN13) {
        Book searchBook = bookService.getBookByISBN13(ISBN13);
        return new ResponseEntity<>(searchBook, HttpStatus.OK);
    }

    @GetMapping("/getbookbytitle/{title}")
    public ResponseEntity<List<Book>> getBookByTitle(@PathVariable String title) {
        List<Book> booksByTitle = bookService.getBookByTitle(title);
        return new ResponseEntity<>(booksByTitle, HttpStatus.OK);
    }

    @GetMapping("/getbookbylanguage/{language}")
    public ResponseEntity<List<Book>> getBookByLanguage(@PathVariable String language) {
        List<Book> booksByLanguage = bookService.getBooksByLanguage(language);
        return new ResponseEntity<>(booksByLanguage, HttpStatus.OK);
    }

    @GetMapping("/getbookbyid/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Optional<Book>> getBookById(@PathVariable Long id) {
        Optional<Book> bookById = bookService.findByBookId(id);
        return new ResponseEntity<>(bookById, HttpStatus.OK);
    }

    @DeleteMapping("/deletebook/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> removeBook(@PathVariable Long id) {
        Optional<Book> foundBook = bookService.findByBookId(id);
        if (foundBook.isEmpty()){
            throw new NotFoundException("No data available of book ID: " + id);
        }
        bookService.removeBook(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/getlistofbooks")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Iterable<Book>> getAllBooks() {
        Iterable<Book> allBooks = bookService.findAllBooks();
        return new ResponseEntity<>(allBooks, HttpStatus.OK);
    }

    @PatchMapping("/updatebookinformation")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Book> updateBook1(@RequestBody Book book) {
        Book updateBook = bookService.updateBook1(book);
        return new ResponseEntity<>(updateBook, HttpStatus.OK);
    }
}