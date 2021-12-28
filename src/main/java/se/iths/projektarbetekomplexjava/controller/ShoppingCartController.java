package se.iths.projektarbetekomplexjava.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import se.iths.projektarbetekomplexjava.entity.*;
import se.iths.projektarbetekomplexjava.exception.NotFoundException;
import se.iths.projektarbetekomplexjava.repository.*;
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
                                  CartItemService cartItemService, BookService bookService
                                   ) {
        this.shoppingCartService = shoppingCartService;
        this.customerRepository = customerRepository;
        this.shoppingCartRepository = shoppingCartRepository;
        this.cartItemService = cartItemService;
        this.bookService = bookService;

    }


    @PutMapping("/addbooks/bookid/{bookid}/username/{username}/{qty}")
    public ResponseEntity<CartItem> addBooksToCart(@PathVariable int qty,@PathVariable String username, @PathVariable Long bookid) {
        Customer customer = customerRepository.findByUsername(username);
        ShoppingCart shoppingCart=customer.getShoppingCart();
        Book book;
        book = bookService.findByBookId(bookid).orElseThrow(EntityNotFoundException::new);

        if (qty > book.getStock().getQuantity()) {
            throw new NotFoundException("books in stock are not enough for your request");
        }
        CartItem cartItem = cartItemService.addBookToCart(book, customer, qty);
        shoppingCartService.updateShoppingCart(shoppingCart);
        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }

    @PutMapping("/updateCartItem/{cartid}/quantity/{qty}")
    public ResponseEntity<CartItem>   updateCartItem(@PathVariable Long cartid,@PathVariable int qty){
        CartItem cartItem=cartItemService.findById(cartid);
        cartItem.setQty(qty);
        cartItemService.updateCartItem(cartItem);
        return new ResponseEntity<>(cartItem,HttpStatus.OK);

    }


    @DeleteMapping("/removebookfromcart/{id}")
    public ResponseEntity<Void> removeBookFromCart(@PathVariable Long id) {
       cartItemService.removeCartItem(cartItemService.findById(id));
        return new ResponseEntity<>(HttpStatus.RESET_CONTENT);
    }

    @PutMapping("/updateShoppingCart/username/{uname}")
    public ResponseEntity<ShoppingCart> updateShoppingCart(@PathVariable String uname){
        Customer customer=customerRepository.findByUsername(uname);
        ShoppingCart shoppingCart=customer.getShoppingCart();
      //  List<CartItem> cartItemList=cartItemService.findByShoppingCart(shoppingCart);
        shoppingCartService.updateShoppingCart(shoppingCart);
        return new ResponseEntity<>(shoppingCart,HttpStatus.ACCEPTED);

    }

}