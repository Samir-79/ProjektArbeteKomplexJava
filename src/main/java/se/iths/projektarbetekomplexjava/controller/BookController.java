package se.iths.projektarbetekomplexjava.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.iths.projektarbetekomplexjava.entity.Book;
import se.iths.projektarbetekomplexjava.entity.Customer;
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
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        Book addedBook = bookService.addBook(book);
        return new ResponseEntity<>(addedBook, HttpStatus.CREATED);
    }

    @GetMapping("/getbookbyisbn")
    public  ResponseEntity<Book> getByISBN(@PathVariable String ISBN13) {
        Book searchBook = bookService.getBookByISBN13(ISBN13);
        return  new ResponseEntity<>(searchBook, HttpStatus.OK);
    }

    @GetMapping("/getbookbyauthor")
    public  ResponseEntity<List<Book>> getBooksByAuthor(@PathVariable String firstName, String lastName){
        List<Book> booksByAuthor = bookService.getBooksByAuthor(firstName,lastName);
        return  new ResponseEntity<>(booksByAuthor, HttpStatus.OK);
    }

    @GetMapping("/getbookbytitle")
    public ResponseEntity<List<Book>> getBookByTitle(@PathVariable String title) {
        List<Book> booksByTitle = bookService.getBookByTitle(title);
        return new ResponseEntity<>(booksByTitle,HttpStatus.OK);
    }

    @GetMapping("/getbookbylanguage")
    public ResponseEntity<List<Book>> getBookByLanguage(@PathVariable String language) {
        List<Book> booksByLanguage = bookService.getBooksByLanguage(language);
        return  new ResponseEntity<>(booksByLanguage,HttpStatus.OK);
    }

    @GetMapping("/getbookbyid")
    public ResponseEntity<Optional<Book>> getBookById(@PathVariable Long id) {
        Optional<Book> bookById = bookService.findByBookId(id);
        return new ResponseEntity<>(bookById,HttpStatus.OK);
    }

    @DeleteMapping("/deletebook/{id}")
    public  ResponseEntity<Void> removeBoook(@PathVariable Long id) {
        bookService.removeBook(id);
        return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/getlistofbooks")
    public ResponseEntity<Iterable<Book>> getAllBooks() {
        Iterable<Book> allBooks = bookService.findAllBooks();
        return  new ResponseEntity<>(allBooks,HttpStatus.OK);
    }

    @PutMapping("/updatebook")
    public ResponseEntity<Book> updateBook(@RequestBody Book book) {
        Book updateBook = bookService.updateBook(book);
        return  new ResponseEntity<>(updateBook,HttpStatus.OK);
    }

    @PatchMapping("/updatebookinformation")
    public ResponseEntity<Book> updateBook1(@RequestBody Book book) {
        Book updateBook = bookService.updateBook1(book);
        return  new ResponseEntity<>(updateBook,HttpStatus.OK);
    }
}