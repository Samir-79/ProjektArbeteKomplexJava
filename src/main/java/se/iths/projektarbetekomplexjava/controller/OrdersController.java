package se.iths.projektarbetekomplexjava.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.web.bind.annotation.*;
import se.iths.projektarbetekomplexjava.entity.Customer;
import se.iths.projektarbetekomplexjava.entity.Orders;
import se.iths.projektarbetekomplexjava.entity.ShoppingCart;
import se.iths.projektarbetekomplexjava.entity.mailsender.MailConstructor;
import se.iths.projektarbetekomplexjava.repository.CustomerRepository;
import se.iths.projektarbetekomplexjava.repository.ShoppingCartRepository;
import se.iths.projektarbetekomplexjava.service.CartItemService;
import se.iths.projektarbetekomplexjava.service.CustomerService;
import se.iths.projektarbetekomplexjava.service.OrderService;
import se.iths.projektarbetekomplexjava.service.ShoppingCartService;

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
    private final MailConstructor mailConstructor;
    private final JavaMailSender mailSender;


    public OrdersController(ShoppingCartService shoppingCartService,
                            CustomerRepository customerRepository,
                            ShoppingCartRepository shoppingCartRepository,
                            CartItemService cartItemService, CustomerService customerService,
                            OrderService orderService,MailConstructor mailConstructor,JavaMailSender mailSender
                            ) {
        this.shoppingCartService = shoppingCartService;
        this.customerRepository = customerRepository;
        this.shoppingCartRepository = shoppingCartRepository;
        this.cartItemService = cartItemService;
        this.customerService = customerService;
        this.orderService = orderService;
        this.mailConstructor=mailConstructor;
        this.mailSender = mailSender;
    }

    @PostMapping("/createorder/userid/{userid}")
    public ResponseEntity<Orders> createOrder(HttpServletRequest request,@RequestBody Orders orders, @PathVariable Long userid) {
        Customer customer = customerService.findUserById(userid).orElseThrow(EntityNotFoundException::new);
        ShoppingCart foundShoppingCart = customer.getShoppingCart();

        Orders order = orderService.createOrder(orders, foundShoppingCart);
        String appUrl = "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
       MimeMessagePreparator email =mailConstructor.OrderConfirmationEmail(customer,order);
       mailSender.send(email);


        return new ResponseEntity<>(order, HttpStatus.CREATED);


    }
}
