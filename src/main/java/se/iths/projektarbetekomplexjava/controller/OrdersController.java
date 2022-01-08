package se.iths.projektarbetekomplexjava.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.iths.projektarbetekomplexjava.entity.Customer;
import se.iths.projektarbetekomplexjava.entity.Orders;
import se.iths.projektarbetekomplexjava.entity.ShoppingCart;
import se.iths.projektarbetekomplexjava.repository.CustomerRepository;
import se.iths.projektarbetekomplexjava.repository.ShoppingCartRepository;
import se.iths.projektarbetekomplexjava.service.CartItemService;
import se.iths.projektarbetekomplexjava.service.CustomerService;
import se.iths.projektarbetekomplexjava.service.OrderService;
import se.iths.projektarbetekomplexjava.service.ShoppingCartService;
import se.iths.projektarbetekomplexjava.service.EmailVerification;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("bokhandel/api/v1/orders/")
public class OrdersController {

    private final ShoppingCartService shoppingCartService;
    private final CustomerRepository customerRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemService cartItemService;
    private final CustomerService customerService;
    private final OrderService orderService;
    private final EmailVerification emailVerification;


    public OrdersController(ShoppingCartService shoppingCartService,
                            CustomerRepository customerRepository,
                            ShoppingCartRepository shoppingCartRepository,
                            CartItemService cartItemService, CustomerService customerService,
                            OrderService orderService, EmailVerification emailVerification
    ) {
        this.shoppingCartService = shoppingCartService;
        this.customerRepository = customerRepository;
        this.shoppingCartRepository = shoppingCartRepository;
        this.cartItemService = cartItemService;
        this.customerService = customerService;
        this.orderService = orderService;
        this.emailVerification = emailVerification;
    }

    @PostMapping("/createorder/userid/{userid}")
    public ResponseEntity<Orders> createOrder(@RequestBody Orders orders, @PathVariable Long userid) {
        Customer customer = customerService.findUserById(userid).orElseThrow(EntityNotFoundException::new);
        ShoppingCart foundShoppingCart = customer.getShoppingCart();

        Orders order = orderService.createOrder(orders, foundShoppingCart);
        emailVerification.sendOrderConfirmationEmail(customer, order);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }
}