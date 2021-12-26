package se.iths.projektarbetekomplexjava.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.iths.projektarbetekomplexjava.entity.BooksInShoppingCart;
import se.iths.projektarbetekomplexjava.repository.*;
import se.iths.projektarbetekomplexjava.service.BookService;
import se.iths.projektarbetekomplexjava.service.BooksInShoppingCartService;

@RestController
@RequestMapping("bokhandel/api/v1/booksinshoppingcart/")
public class BooksInShoppingCartController {

    public final BooksInShoppingCartRepository booksInShoppingCartRepository;
    public final BooksInShoppingCartService booksInShoppingCartService;
    public final CustomerRepository customerRepository;
    public final ShoppingCartRepository shoppingCartRepository;
    public final BookService bookService;

    public BooksInShoppingCartController(BooksInShoppingCartRepository booksInShoppingCartRepository, BooksInShoppingCartService  booksInShoppingCartService, CustomerRepository customerRepository, ShoppingCartRepository shoppingCartRepository, BookService bookService) {
        this.booksInShoppingCartRepository = booksInShoppingCartRepository;
        this.booksInShoppingCartService = booksInShoppingCartService;
        this.customerRepository = customerRepository;
        this.shoppingCartRepository = shoppingCartRepository;
        this.bookService = bookService;
    }

    @PutMapping("/addbooks/{bookId}")
    public ResponseEntity<BooksInShoppingCart> addBooksToCustomersShoppingCart(@PathVariable Long bookId, @RequestBody BooksInShoppingCart booksInShoppingCart) {
        BooksInShoppingCart updateBooksInShoppingCart = booksInShoppingCartService.addBookToBooksInShoppingCart(bookId,booksInShoppingCart);
        return new ResponseEntity<>(updateBooksInShoppingCart, HttpStatus.OK);
    }
}
