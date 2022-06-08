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
import se.iths.projektarbetekomplexjava.service.UserService;

import java.io.IOException;
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
    private final CartItemRepository cartItemRepository;
    private final UserService userService;

    public ShoppingCartController(ShoppingCartService shoppingCartService,
                                  CustomerRepository customerRepository,
                                  ShoppingCartRepository shoppingCartRepository,
                                  CartItemService cartItemService, BookService bookService,
                                  CartItemRepository cartItemRepository,
                                  UserService userService) {
        this.shoppingCartService = shoppingCartService;
        this.customerRepository = customerRepository;
        this.shoppingCartRepository = shoppingCartRepository;
        this.cartItemService = cartItemService;
        this.bookService = bookService;
        this.cartItemRepository = cartItemRepository;
        this.userService = userService;
    }

    @PostMapping("addbooks")
    public Object addBooksToCart(@RequestHeader(value="Authorization") String authorization, @RequestParam("qty") int qty,  @RequestParam("bookid") Long bookid) {
        String username=userService.getUserName(authorization);
        Optional<Customer> customer = customerRepository.findByUsername(username);
        if (customer.isEmpty()) {
            throw new NotFoundException("Customer not found");
        }
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String uname;

        if (principal instanceof UserDetails) {
            uname = ((UserDetails) principal).getUsername();
        } else {
            uname = principal.toString();
        }
        System.out.println(uname);
        if (customer.get().getUsername().equals(uname)) {
            ShoppingCart shoppingCart = customer.get().getShoppingCart();
            Book book;
            book = bookService.findByBookId(bookid).orElseThrow(() -> new NotFoundException("book with Id: " + bookid + " not found"));

            if (qty > book.getStock().getQuantity() || book.getStock().getQuantity() == 0 || qty == 0) {
                throw new BadRequestException("Book quantity is not available");
            }

            CartItem cartItem = cartItemService.addBookToCart(book, customer, qty);
            shoppingCartService.updateShoppingCart(shoppingCart);
            return new ResponseEntity<>(cartItem, HttpStatus.OK);
        }
        return "you have to be logged in to add book to cart";
    }

    @PutMapping("updateCartItem/{cartid}/quantity/{qty}")
    public ResponseEntity<CartItem> updateCartItem(@PathVariable Long cartid, @PathVariable int qty) {
        CartItem cartItem = cartItemService.findById(cartid);
        if (cartItem.getQty() + qty > 150) {
            throw new BadRequestException("Number of copies should not exceed 150!");
        }
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
        } else if (cartItem.getQty() == qty) {
            try {
                return "this will remove all the copies of this book from the cart";
            } finally {
                cartItemService.removeCartItem(cartItemService.findById(cartid));
            }
        }

        cartItem.setQty(cartItem.getQty() - qty);
        cartItemService.removeBookFromCart(cartItem);
        shoppingCartService.updateShoppingCart(cartItem.getShoppingCart());

        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }

    @DeleteMapping("/removecartItem")
    public ResponseEntity<Void> removeBookFromCart(@RequestParam Long cartItemId) {
        cartItemService.removeCartItem(cartItemService.findById(cartItemId));
        return new ResponseEntity<>(HttpStatus.RESET_CONTENT);
    }
    @DeleteMapping("/removecartItem1")
    public ResponseEntity<CartItem> removeCartItem1(@RequestHeader(value="Authorization") String authorization, @RequestParam Long bookid) {
        String username=userService.getUserName(authorization);
        CartItem foundCartItem =  cartItemRepository.findCartItemByUserNameAndBookId(username,bookid);
        cartItemService.removeCartItem(cartItemService.findById(foundCartItem.getId()));
        return new ResponseEntity<>(HttpStatus.RESET_CONTENT);
    }

    @GetMapping("/getCartItemList")
    public ResponseEntity<Iterable<CartItem>> getCartItemList(@RequestParam Long shoppingCartId){
        List<CartItem> cartItemList = cartItemService.getCartItemList(shoppingCartId);
        return new ResponseEntity<>(cartItemList, HttpStatus.OK);
    }

    @GetMapping("/getCartItemList1")
    public ResponseEntity<Iterable<CartItem>> getCartItemList(@RequestHeader(value="Authorization") String authorization
    ){
        String username=userService.getUserName(authorization);
        ShoppingCart foundShoppingCart = shoppingCartRepository.findShoppingCartByUserName(username);
        System.out.println(foundShoppingCart.getId());
        List<CartItem> cartItemList = cartItemService.getCartItemList(foundShoppingCart.getId());
        return new ResponseEntity<>(cartItemList, HttpStatus.OK);
    }

    @GetMapping("/getCartItem")
    public ResponseEntity<CartItem> getCartItem(@RequestParam String username, @RequestParam Long bookid) {
      CartItem foundCartItem =  cartItemRepository.findCartItemByUserNameAndBookId(username,bookid);
        return new ResponseEntity<>(foundCartItem,HttpStatus.OK);
    }
}