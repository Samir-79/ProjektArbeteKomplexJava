package se.iths.projektarbetekomplexjava.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.iths.projektarbetekomplexjava.entity.Book;
import se.iths.projektarbetekomplexjava.entity.Customer;
import se.iths.projektarbetekomplexjava.entity.Employee;
import se.iths.projektarbetekomplexjava.entity.ShoppingCart;
import se.iths.projektarbetekomplexjava.repository.CustomerRepository;
import se.iths.projektarbetekomplexjava.repository.ShoppingCartRepository;
import se.iths.projektarbetekomplexjava.service.BookService;
import se.iths.projektarbetekomplexjava.service.ShoppingCartService;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("bokhandel/api/v1/shoppingcart/")
public class ShoppingCartController {

    public final ShoppingCartService shoppingCartService;
    public final CustomerRepository customerRepository;
    public final ShoppingCartRepository shoppingCartRepository;
    public final BookService bookService;

    public ShoppingCartController(ShoppingCartService shoppingCartService, CustomerRepository customerRepository, ShoppingCartRepository shoppingCartRepository, BookService bookService) {
        this.shoppingCartService = shoppingCartService;
        this.customerRepository = customerRepository;
        this.shoppingCartRepository = shoppingCartRepository;
        this.bookService = bookService;
    }


    @PutMapping("/addbooks/{bookId}")
    public ResponseEntity<ShoppingCart> addBooksToShoppingCart(@PathVariable Long bookId,@RequestBody ShoppingCart shoppingCart) {
        ShoppingCart updateShoppingCart = shoppingCartService.addBookToShoppingCart(bookId,shoppingCart);
        return new ResponseEntity<>(updateShoppingCart, HttpStatus.OK);
    }

    @DeleteMapping("/deleteShoppingCart/{id}")
    public ResponseEntity<Void> removeShoppingCart(@PathVariable Long id) {
        shoppingCartService.deleteShoppingCart(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
