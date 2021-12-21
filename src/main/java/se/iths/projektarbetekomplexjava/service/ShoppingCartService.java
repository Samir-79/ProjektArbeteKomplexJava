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
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class ShoppingCartService {

    private ShoppingCartRepository shoppingCartRepository;
    private BookRepository bookRepository;
    private CustomerRepository customerRepository;
    private OrderRepository orderRepository;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository,
                               BookRepository bookRepository,
                               CustomerRepository customerRepository,
                               OrderRepository orderRepository) {

        this.shoppingCartRepository = shoppingCartRepository;
        this.bookRepository = bookRepository;
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
    }

    public ShoppingCart addBookToShoppingCart(ShoppingCart shoppingCart, Long bookId) {
        ShoppingCart foundShoppingCart = shoppingCartRepository.findById(shoppingCart.getId()).orElseThrow(EntityNotFoundException::new);
        Book foundBook = bookRepository.findById(bookId).orElseThrow(EntityNotFoundException::new);
        foundShoppingCart.setPrice((double) (shoppingCart.getQty()* foundBook.getPrice()));
        foundShoppingCart.setQty(shoppingCart.getQty());
        //System.out.println(Arrays.toString(book.toArray()));
        foundShoppingCart.addBook(foundBook);
        //foundShoppingCart.addBook(bookService.addBook(book.stream().findFirst().get()));
        //foundShoppingCart.setBooks(foundShoppingCart.getBooks());
        //bookRepository.save(book);
        //foundShoppingCart.setBooks(foundShoppingCart.getBooks());
        return shoppingCartRepository.save(foundShoppingCart);
    }

    public void deleteShoppingCart(Long id) {
        ShoppingCart foundShoppingCart = shoppingCartRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        shoppingCartRepository.deleteById(foundShoppingCart.getId());
    }
}
