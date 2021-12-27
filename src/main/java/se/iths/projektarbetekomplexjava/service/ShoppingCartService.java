package se.iths.projektarbetekomplexjava.service;

import org.springframework.stereotype.Service;
import se.iths.projektarbetekomplexjava.entity.Book;
import se.iths.projektarbetekomplexjava.entity.CartItem;
import se.iths.projektarbetekomplexjava.entity.Customer;
import se.iths.projektarbetekomplexjava.entity.ShoppingCart;
import se.iths.projektarbetekomplexjava.repository.BookRepository;
import se.iths.projektarbetekomplexjava.repository.CustomerRepository;
import se.iths.projektarbetekomplexjava.repository.OrderRepository;
import se.iths.projektarbetekomplexjava.repository.ShoppingCartRepository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.*;

@Transactional
@Service
public class ShoppingCartService {

    private static int totalQuantity;

    private final ShoppingCartRepository shoppingCartRepository;
    private final BookRepository bookRepository;
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final BookService bookService;
    private final CartItemService cartItemService;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository,
                               BookRepository bookRepository, CustomerRepository customerRepository,
                               OrderRepository orderRepository, BookService bookService,
                               CartItemService cartItemService) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.bookRepository = bookRepository;
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
        this.bookService = bookService;
        this.cartItemService = cartItemService;
    }


    public ShoppingCart updateShoppingCart(ShoppingCart shoppingCart) {
        Double cartTotal=0.0;

        List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);

        for(CartItem cartItem : cartItemList) {
            if(cartItem.getBook().getStock().isInStock()) {
                cartItemService.updateCartItem(cartItem);
                cartTotal = cartTotal+(cartItem.getSubtotal());
            }
        }

        shoppingCart.setGrandTotal(cartTotal);

        shoppingCartRepository.save(shoppingCart);

        return shoppingCart;
    }






    //    public ShoppingCart addBookToShoppingCart(ShoppingCart shoppingCart, Long bookId) {
//        ShoppingCart foundShoppingCart = shoppingCartRepository.findById(shoppingCart.getId()).orElseThrow(EntityNotFoundException::new);
//        Book foundBook = bookRepository.findById(bookId).orElseThrow(EntityNotFoundException::new);
//        foundShoppingCart.setPrice((double) (shoppingCart.getQty()* foundBook.getPrice()));
//        foundShoppingCart.setQty(shoppingCart.getQty());
//        //System.out.println(Arrays.toString(book.toArray()));
//        foundShoppingCart.addBook(foundBook);
//        //foundShoppingCart.addBook(bookService.addBook(book.stream().findFirst().get()));
//        //foundShoppingCart.setBooks(foundShoppingCart.getBooks());
//        //bookRepository.save(book);
//        //foundShoppingCart.setBooks(foundShoppingCart.getBooks());
//        return shoppingCartRepository.save(foundShoppingCart);
//    }

    /*public ShoppingCart addBookToShoppingCart(Long bookId, ShoppingCart shoppingCart) {
        ShoppingCart foundShoppingCart = shoppingCartRepository.findById(shoppingCart.getId()).orElseThrow(EntityNotFoundException::new);
        Book foundBook = bookRepository.findById(bookId).orElseThrow(EntityNotFoundException::new);
//if (foundBook.getStock().getQuantity() > 0) {
        if (checkIfBookInShoppingCart(foundBook, foundShoppingCart)) {
            foundShoppingCart.setQty(foundShoppingCart.getQty() + shoppingCart.getQty());
            foundShoppingCart.setPrice((double) (shoppingCart.getQty() * foundBook.getPrice()));
            return shoppingCartRepository.save(foundShoppingCart);
        }
        else{
            foundShoppingCart.setQty(shoppingCart.getQty());
            foundShoppingCart.addBook(foundBook);
            foundShoppingCart.setPrice((double) (shoppingCart.getQty() * foundBook.getPrice()));
            return shoppingCartRepository.save(foundShoppingCart);
        }
        //totalQuantity = totalQuantity + shoppingCart.getQty();
// } else
// System.out.println("book not available");
//System.out.println(Arrays.toString(book.toArray()));

//foundShoppingCart.addBook(bookService.addBook(book.stream().findFirst().get()));
//foundShoppingCart.setBooks(foundShoppingCart.getBooks());
//bookRepository.save(book);
//foundShoppingCart.setBooks(foundShoppingCart.getBooks());
       // System.out.println("###############################" + totalQuantity + "################################");
       // return null;
    }


    public boolean checkIfBookInShoppingCart(Book book, ShoppingCart foundShoppingCart) {
        for (Book book1 : foundShoppingCart.getBooks()) {
            if (book1.getISBN13().equals(book.getISBN13())) {
                return true;
            }
        }
        return false;
    }*/

    public void deleteShoppingCart(Long id) {
        ShoppingCart foundShoppingCart = shoppingCartRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        shoppingCartRepository.deleteById(foundShoppingCart.getId());
    }
}
