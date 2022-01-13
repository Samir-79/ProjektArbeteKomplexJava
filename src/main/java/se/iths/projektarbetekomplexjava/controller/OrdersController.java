package se.iths.projektarbetekomplexjava.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import java.util.List;


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

    @PutMapping("/updateorder")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Orders> updateorder(@RequestBody Orders ordres) {
        Orders order= orderService.findOrderById(ordres.getId());
        return  new ResponseEntity<>(order, HttpStatus.OK);
    }

    @DeleteMapping("/deleteorder/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteOrder(@PathVariable Long id){
        orderService.removeOrder(id);
        return new ResponseEntity<>("Order:"+ id+ "removed from repository",HttpStatus.OK);

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