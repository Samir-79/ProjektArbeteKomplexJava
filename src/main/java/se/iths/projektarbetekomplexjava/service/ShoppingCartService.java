package se.iths.projektarbetekomplexjava.service;

import org.springframework.stereotype.Service;
import se.iths.projektarbetekomplexjava.entity.Book;
import se.iths.projektarbetekomplexjava.entity.CartItem;
import se.iths.projektarbetekomplexjava.entity.Customer;
import se.iths.projektarbetekomplexjava.entity.ShoppingCart;
import se.iths.projektarbetekomplexjava.repository.*;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Transactional
@Service
public class ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final BookRepository bookRepository;
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final BookService bookService;
    private final CartItemService cartItemService;
    private final BookToCartRepository bookToCartRepository;
    private final CartItemRepository cartItemRepository;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository,
                               BookRepository bookRepository, CustomerRepository customerRepository,
                               OrderRepository orderRepository, BookService bookService,
                               CartItemService cartItemService, BookToCartRepository bookToCartRepository,
                               CartItemRepository cartItemRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.bookRepository = bookRepository;
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
        this.bookService = bookService;
        this.cartItemService = cartItemService;
        this.bookToCartRepository = bookToCartRepository;
        this.cartItemRepository = cartItemRepository;
    }


    public ShoppingCart updateShoppingCart(ShoppingCart shoppingCart) {
        Double grandTotalPrice = 0.0;
        int totalNumberOfBooks=0;

        List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);

        for (CartItem cartItem : cartItemList) {
            if (cartItem.getBook().getStock().getQuantity()>0) {
                cartItemService.updateCartItem(cartItem);
                grandTotalPrice = grandTotalPrice + (cartItem.getSubtotal());
                totalNumberOfBooks=totalNumberOfBooks+(cartItem.getQty());
            }
        }

        shoppingCart.setGrandTotal(grandTotalPrice);
        shoppingCart.setTotalNumberOfBooks(totalNumberOfBooks);
        shoppingCartRepository.save(shoppingCart);

        return shoppingCart;
    }



    public void clearShoppingCart(ShoppingCart shoppingCart) {
        List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);

        for(CartItem cartItem : cartItemList) {
            cartItem.setShoppingCart(null);
            cartItemService.save(cartItem);
        }

        shoppingCart.setGrandTotal(0.0);

        shoppingCartRepository.save(shoppingCart);
    }
}
