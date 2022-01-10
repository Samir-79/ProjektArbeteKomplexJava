package se.iths.projektarbetekomplexjava.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import se.iths.projektarbetekomplexjava.entity.*;
import se.iths.projektarbetekomplexjava.exception.NotFoundException;
import se.iths.projektarbetekomplexjava.repository.*;
import se.iths.projektarbetekomplexjava.service.BookService;
import se.iths.projektarbetekomplexjava.service.CartItemService;
import se.iths.projektarbetekomplexjava.service.ShoppingCartService;

import javax.persistence.EntityNotFoundException;

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
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String uname;

        if (principal instanceof UserDetails) {
            uname = ((UserDetails)principal).getUsername();
            System.out.println("===="+uname+"======");
        } else {
             uname = principal.toString();
            System.out.println("===="+uname+"======");
        }
        if(customer.getUsername().equals(uname)) {
            ShoppingCart shoppingCart = customer.getShoppingCart();
            Book book;
            book = bookService.findByBookId(bookid).orElseThrow(EntityNotFoundException::new);

            if (qty > book.getStock().getQuantity()) {
                throw new NotFoundException("books in stock are not enough for your request");
            }
            CartItem cartItem = cartItemService.addBookToCart(book, customer, qty);
            //auto increment number of books and subtotal
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
    public ResponseEntity<CartItem> removeBookFromCartItem(@PathVariable Long cartid, @PathVariable int qty) {
        CartItem cartItem = cartItemService.findById(cartid);
        if(cartItem.getQty() < qty) {
            throw new NotFoundException("you do not have : " + qty + " books in your cart");

        }
        else if(cartItem.getQty() == qty){
            try {
                throw new NotFoundException("this will remove all the copies of this book from the cart");
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

    @DeleteMapping("/removebookfromcart/{id}")
    public ResponseEntity<Void> removeBookFromCart(@PathVariable Long id) {
        cartItemService.removeCartItem(cartItemService.findById(id));
        return new ResponseEntity<>(HttpStatus.RESET_CONTENT);
    }

    @PutMapping("/updateShoppingCart/username/{uname}")
    public ResponseEntity<ShoppingCart> updateShoppingCart(@PathVariable String uname) {
        Customer customer = customerRepository.findByUsername(uname);
        ShoppingCart shoppingCart = customer.getShoppingCart();
        //  List<CartItem> cartItemList=cartItemService.findByShoppingCart(shoppingCart);
        shoppingCartService.updateShoppingCart(shoppingCart);
        return new ResponseEntity<>(shoppingCart, HttpStatus.ACCEPTED);
    }
}