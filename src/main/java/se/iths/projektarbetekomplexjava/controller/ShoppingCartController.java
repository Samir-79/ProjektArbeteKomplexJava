package se.iths.projektarbetekomplexjava.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import se.iths.projektarbetekomplexjava.entity.*;
import se.iths.projektarbetekomplexjava.exception.NotFoundException;
import se.iths.projektarbetekomplexjava.repository.BookRepository;
import se.iths.projektarbetekomplexjava.repository.CustomerRepository;
import se.iths.projektarbetekomplexjava.repository.ShoppingCartRepository;
import se.iths.projektarbetekomplexjava.service.BookService;
import se.iths.projektarbetekomplexjava.service.CartItemService;
import se.iths.projektarbetekomplexjava.service.ShoppingCartService;

import javax.persistence.EntityNotFoundException;
import javax.websocket.server.PathParam;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("bokhandel/api/v1/shoppingcart/")
public class ShoppingCartController {

    public final ShoppingCartService shoppingCartService;
    public final CustomerRepository customerRepository;
    public final ShoppingCartRepository shoppingCartRepository;
    public final CartItemService cartItemService;
    private final BookService bookService;


    public ShoppingCartController(ShoppingCartService shoppingCartService,
                                  CustomerRepository customerRepository,
                                  ShoppingCartRepository shoppingCartRepository,
                                  CartItemService cartItemService, BookService bookService) {
        this.shoppingCartService = shoppingCartService;
        this.customerRepository = customerRepository;
        this.shoppingCartRepository = shoppingCartRepository;
        this.cartItemService = cartItemService;
        this.bookService = bookService;
    }


    @PutMapping("/addbooks/{qty}/username/{username}/bookid/{bookid}")
    public ResponseEntity<CartItem> addBooksToShoppingCart(@PathVariable int qty,@PathVariable String username, @PathVariable Long bookid) {
        Customer customer = customerRepository.findByUsername(username);
        Book book;
        book = bookService.findByBookId(bookid).orElseThrow(EntityNotFoundException::new);

        if (qty > book.getStock().getQuantity()) {
            throw new NotFoundException("books in stock are not enough for your request");
        }
        CartItem cartItem = cartItemService.addBookToCart(book, customer, qty);

        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }
    @PutMapping("/updateShoppingCart/{cartid}/quantity/{qty}")
    public ResponseEntity<CartItem>   updateShoppingCart(@PathVariable Long cartid,@PathVariable int qty){
        CartItem cartItem=cartItemService.findById(cartid);
        cartItem.setQty(qty);
        cartItemService.updateCartItem(cartItem);
        return new ResponseEntity<>(cartItem,HttpStatus.OK);

    }

    @DeleteMapping("/deleteShoppingCart/{id}")
    public ResponseEntity<Void> removeShoppingCart(@PathVariable Long id) {
        shoppingCartService.deleteShoppingCart(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}