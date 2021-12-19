package se.iths.projektarbetekomplexjava.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.iths.projektarbetekomplexjava.entity.Customer;
import se.iths.projektarbetekomplexjava.entity.ShoppingCart;
import se.iths.projektarbetekomplexjava.repository.CustomerRepository;
import se.iths.projektarbetekomplexjava.service.ShoppingCartService;

import java.util.Optional;

@RestController
@RequestMapping("bokhandel/api/vi/customer")
public class ShoppingCartController {

    public final ShoppingCartService shoppingCartService;
    public final CustomerRepository customerRepository;

    public ShoppingCartController(ShoppingCartService shoppingCartService, CustomerRepository customerRepository) {
        this.shoppingCartService = shoppingCartService;
        this.customerRepository = customerRepository;
    }


    @PutMapping("/addbooks/{customerId}")
    public ResponseEntity<ShoppingCart> addBooksToShoppingCart(@PathVariable Long customerId) {

        ShoppingCart foundShoppingCart = shoppingCartService.addBookToShoppingCart(customerId);

        return new ResponseEntity<>(foundShoppingCart, HttpStatus.OK);

    }


}
