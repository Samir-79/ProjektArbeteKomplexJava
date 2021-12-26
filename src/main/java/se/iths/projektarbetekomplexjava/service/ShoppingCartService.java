package se.iths.projektarbetekomplexjava.service;

import org.springframework.stereotype.Service;
import se.iths.projektarbetekomplexjava.entity.Book;
import se.iths.projektarbetekomplexjava.entity.Customer;
import se.iths.projektarbetekomplexjava.entity.ShoppingCart;
import se.iths.projektarbetekomplexjava.repository.BookRepository;
import se.iths.projektarbetekomplexjava.repository.CustomerRepository;
import se.iths.projektarbetekomplexjava.repository.OrderRepository;
import se.iths.projektarbetekomplexjava.repository.ShoppingCartRepository;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@Service
public class ShoppingCartService {

    private static int totalQuantity;

    private ShoppingCartRepository shoppingCartRepository;
    private BookRepository bookRepository;
    private CustomerRepository customerRepository;
    private OrderRepository orderRepository;
    private BookService bookService;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository,
                               BookRepository bookRepository,
                               CustomerRepository customerRepository,
                               OrderRepository orderRepository,
                               BookService bookService) {

        this.shoppingCartRepository = shoppingCartRepository;
        this.bookRepository = bookRepository;
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
        this.bookService = bookService;
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

    public ShoppingCart addBookToShoppingCart(Long bookId, ShoppingCart shoppingCart) {
        ShoppingCart foundShoppingCart = shoppingCartRepository.findById(shoppingCart.getId()).orElseThrow(EntityNotFoundException::new);
        Book foundBook = bookRepository.findById(bookId).orElseThrow(EntityNotFoundException::new);
        //foundShoppingCart.setPrice((double) (shoppingCart.getQty()* foundBook.getPrice()));
//if (foundBook.getStock().getQuantity() > 0) {
//        if (checkIfBookInShoppingCart(foundBook, foundShoppingCart)) {
//            int updateNrCopies= foundShoppingCart.getQty().stream().findFirst().get().getNumberOfCopies();
//                    updateNrCopies+= shoppingCart.getQty().stream().findFirst().get().getNumberOfCopies();
//            //foundShoppingCart.setQty()  ;
//        } else {
//            foundShoppingCart.setQty(shoppingCart.getQty());
//
//        }
        //totalQuantity = totalQuantity + shoppingCart.getQty();
// } else
// System.out.println("book not available");
//System.out.println(Arrays.toString(book.toArray()));
        foundShoppingCart.addBook(foundBook);
//foundShoppingCart.addBook(bookService.addBook(book.stream().findFirst().get()));
//foundShoppingCart.setBooks(foundShoppingCart.getBooks());
//bookRepository.save(book);
//foundShoppingCart.setBooks(foundShoppingCart.getBooks());
       // System.out.println("###############################" + totalQuantity + "################################");
        return shoppingCartRepository.save(foundShoppingCart);
    }


    public boolean checkIfBookInShoppingCart(Book book, ShoppingCart foundShoppingCart) {
        for (Book book1 : foundShoppingCart.getBooks()) {
            if (book1.getISBN13().equals(book.getISBN13())) {
                return true;
            }
        }
        return false;
    }

    public void deleteShoppingCart(Long id) {
        ShoppingCart foundShoppingCart = shoppingCartRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        shoppingCartRepository.deleteById(foundShoppingCart.getId());
    }
}