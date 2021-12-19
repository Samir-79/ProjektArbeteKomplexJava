package se.iths.projektarbetekomplexjava.service;

import org.springframework.stereotype.Service;
import se.iths.projektarbetekomplexjava.entity.Book;
import se.iths.projektarbetekomplexjava.entity.Customer;
import se.iths.projektarbetekomplexjava.entity.ShoppingCart;
import se.iths.projektarbetekomplexjava.repository.BookRepository;
import se.iths.projektarbetekomplexjava.repository.CustomerRepository;
import se.iths.projektarbetekomplexjava.repository.OrderRepository;
import se.iths.projektarbetekomplexjava.repository.ShoppingCartRepository;

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

    public ShoppingCart addBookToShoppingCart(Long customerId) {
        Optional<Customer> foundCustomer = customerRepository.findById(customerId);
        ShoppingCart shoppingCart=foundCustomer.get().getShoppingCart();
        shoppingCart.addBook(shoppingCart.getBooks().stream().findAny().get());
        shoppingCartRepository.save(shoppingCart);
        return shoppingCart;

    }

    public void deleteShoppingCart(ShoppingCart shoppingCart) {
        shoppingCartRepository.deleteAll();

    }
}
