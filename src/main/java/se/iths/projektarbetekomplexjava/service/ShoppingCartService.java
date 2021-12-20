package se.iths.projektarbetekomplexjava.service;

import org.springframework.stereotype.Service;
import se.iths.projektarbetekomplexjava.entity.Book;
import se.iths.projektarbetekomplexjava.entity.ShoppingCart;
import se.iths.projektarbetekomplexjava.exception.NotFoundException;
import se.iths.projektarbetekomplexjava.repository.BookRepository;
import se.iths.projektarbetekomplexjava.repository.CustomerRepository;
import se.iths.projektarbetekomplexjava.repository.OrderRepository;
import se.iths.projektarbetekomplexjava.repository.ShoppingCartRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

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

    public ShoppingCart addingBookToShoppingCart(ShoppingCart shoppingCartRequest) {
        ShoppingCart shoppingCart=new ShoppingCart();
        shoppingCart.setQty(shoppingCartRequest.getQty());
        shoppingCart.setBooks(shoppingCartRequest.getBooks());
        shoppingCart.setOrders(shoppingCartRequest.getOrders());
        shoppingCart.setCustomer(shoppingCartRequest.getCustomer());

        return shoppingCartRepository.save(shoppingCart);

    }

    public void deleteShoppingCartById(Long id) {
        ShoppingCart foundCart= shoppingCartRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        shoppingCartRepository.deleteById(foundCart.getId());
    }

    public Optional<ShoppingCart> getCartById(Long id) {
        return shoppingCartRepository.findById(id);
    }
}