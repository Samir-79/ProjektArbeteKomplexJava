package se.iths.projektarbetekomplexjava.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import se.iths.projektarbetekomplexjava.entity.*;
import se.iths.projektarbetekomplexjava.exception.BadRequestException;
import se.iths.projektarbetekomplexjava.exception.NotFoundException;
import se.iths.projektarbetekomplexjava.repository.*;
import se.iths.projektarbetekomplexjava.service.BookService;
import se.iths.projektarbetekomplexjava.service.CartItemService;
import se.iths.projektarbetekomplexjava.service.ShoppingCartService;

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
    public Object addBooksToCart(@PathVariable int qty, @PathVariable String username, @PathVariable Long bookid) {
        Customer customer = customerRepository.findByUsername(username);
        if (customer == null) {
            throw new NotFoundException("Customer not found");
        }
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String uname;

        if (principal instanceof UserDetails) {
            uname = ((UserDetails) principal).getUsername();
        } else {
            uname = principal.toString();
        }
        if (customer.getUsername().equals(uname)) {
            ShoppingCart shoppingCart = customer.getShoppingCart();
            Book book;
            book = bookService.findByBookId(bookid).orElseThrow(() -> new NotFoundException("book with Id: " + bookid + " not found"));

            if (qty > book.getStock().getQuantity() || book.getStock().getQuantity() == 0 || qty == 0) {
                throw new BadRequestException("Book quantity is not available");
            }

            CartItem cartItem = cartItemService.addBookToCart(book, customer, qty);
            shoppingCartService.updateShoppingCart(shoppingCart);
            return new ResponseEntity<>(cartItem, HttpStatus.OK);
        }
        return "you have to logged in to add book to cart";
    }

    @PutMapping("/updateCartItem/{cartid}/quantity/{qty}")
    public ResponseEntity<CartItem> updateCartItem(@PathVariable Long cartid, @PathVariable int qty) {
        CartItem cartItem = cartItemService.findById(cartid);
        cartItem.setQty(cartItem.getQty() + qty);
        cartItemService.updateCartItem(cartItem);
        shoppingCartService.updateShoppingCart(cartItem.getShoppingCart());
        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }

    @PutMapping("/removeBookFromCart/{cartid}/quantity/{qty}")
    public Object removeBookFromCartItem(@PathVariable Long cartid, @PathVariable int qty) {
        CartItem cartItem = cartItemService.findById(cartid);

        if (cartItem.getQty() < qty) {
            return "you do not have : " + qty + " books in your cart";
        }
        else if (cartItem.getQty() == qty) {
            try {
                return "this will remove all the copies of this book from the cart";
            }
            finally {
                cartItemService.removeCartItem(cartItemService.findById(cartid));
            }
        }

        cartItem.setQty(cartItem.getQty() - qty);
        cartItemService.removeBookFromCart(cartItem);
        shoppingCartService.updateShoppingCart(cartItem.getShoppingCart());

        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }

    @DeleteMapping("/removecartItem/{id}")
    public ResponseEntity<Void> removeBookFromCart(@PathVariable Long id) {
        cartItemService.removeCartItem(cartItemService.findById(id));
        return new ResponseEntity<>(HttpStatus.RESET_CONTENT);
    }
}