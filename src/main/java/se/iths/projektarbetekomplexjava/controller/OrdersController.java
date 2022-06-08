package se.iths.projektarbetekomplexjava.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import se.iths.projektarbetekomplexjava.entity.Customer;
import se.iths.projektarbetekomplexjava.entity.Orders;
import se.iths.projektarbetekomplexjava.entity.ShoppingCart;
import se.iths.projektarbetekomplexjava.exception.NotFoundException;
import se.iths.projektarbetekomplexjava.repository.CustomerRepository;
import se.iths.projektarbetekomplexjava.repository.ShoppingCartRepository;
import se.iths.projektarbetekomplexjava.service.*;
import se.iths.projektarbetekomplexjava.email.EmailVerification;
import java.util.List;
import java.util.Optional;


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
    private final UserService userService;


    public OrdersController(ShoppingCartService shoppingCartService,
                            CustomerRepository customerRepository,
                            ShoppingCartRepository shoppingCartRepository,
                            CartItemService cartItemService, CustomerService customerService,
                            OrderService orderService, EmailVerification emailVerification,
                            UserService userService) {
        this.shoppingCartService = shoppingCartService;
        this.customerRepository = customerRepository;
        this.shoppingCartRepository = shoppingCartRepository;
        this.cartItemService = cartItemService;
        this.customerService = customerService;
        this.orderService = orderService;
        this.emailVerification = emailVerification;
        this.userService = userService;
    }

    @PostMapping("/createorder")
    public ResponseEntity <Optional<Orders>> createOrder(@RequestBody Orders orders,@RequestHeader(value="Authorization") String authorization) {
        String username=userService.getUserName(authorization);
        Optional<Customer> customer = customerService.findByUsername(username);
        if (customer.isEmpty()){
            throw new NotFoundException("No data available of username: " + username);
        }
        ShoppingCart foundShoppingCart = customer.get().getShoppingCart();

        Optional<Orders> order = Optional.ofNullable(orderService.createOrder(orders, foundShoppingCart));
        emailVerification.sendOrderConfirmationEmail(customer, order);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @PutMapping("/updateorder")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Orders> updateorder(@RequestBody Orders orders) {
        Orders updatedOrder= orderService.updateOrder(orders);
        return  new ResponseEntity<>(updatedOrder, HttpStatus.OK);
    }

    @DeleteMapping("/deleteorder/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteOrder(@PathVariable Long id){
        orderService.removeOrder(id);
        return new ResponseEntity<>("Order: "+ id+ " removed from repository",HttpStatus.OK);

    }

    @GetMapping("/getorder/{id}")
    public  ResponseEntity<Orders>  getOrder(@PathVariable Long id) {
        Orders order= orderService.findOrderById(id);
        return new ResponseEntity<>(order,HttpStatus.OK);
    }

    @GetMapping("/getallorders")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity <List<Orders>> getAllOrders(){
        List<Orders>  foundOrders= orderService.findAllOrders();
        return  new ResponseEntity<>(foundOrders,HttpStatus.OK);
    }
}