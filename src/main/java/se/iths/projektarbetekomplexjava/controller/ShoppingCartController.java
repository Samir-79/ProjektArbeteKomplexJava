package se.iths.projektarbetekomplexjava.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.iths.projektarbetekomplexjava.entity.Customer;
import se.iths.projektarbetekomplexjava.entity.Employee;
import se.iths.projektarbetekomplexjava.entity.ShoppingCart;
import se.iths.projektarbetekomplexjava.exception.NotFoundException;
import se.iths.projektarbetekomplexjava.repository.CustomerRepository;
import se.iths.projektarbetekomplexjava.service.ShoppingCartService;

import java.util.Optional;

@RestController
@RequestMapping("bokhandel/api/v1/shoppingCart/")
public class ShoppingCartController {

    public final ShoppingCartService shoppingCartService;
    public final CustomerRepository customerRepository;

    public ShoppingCartController(ShoppingCartService shoppingCartService, CustomerRepository customerRepository) {
        this.shoppingCartService = shoppingCartService;
        this.customerRepository = customerRepository;
    }


    @PutMapping("/addBooks")
    public ResponseEntity<ShoppingCart> addBooksToShoppingCart(@RequestBody ShoppingCart shoppingCart) {
        ShoppingCart updateShoppingCart = shoppingCartService.addBookToShoppingCart(shoppingCart);
        return new ResponseEntity<>(updateShoppingCart, HttpStatus.OK);
    }

    @DeleteMapping("/deleteShoppingCart/{id}")
    public ResponseEntity<Void> removeShoppingCart(@PathVariable Long id){
        shoppingCartService.deleteShoppingCart(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
