package se.iths.projektarbetekomplexjava.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.iths.projektarbetekomplexjava.entity.Book;
import se.iths.projektarbetekomplexjava.entity.Customer;
import se.iths.projektarbetekomplexjava.entity.Orders;
import se.iths.projektarbetekomplexjava.entity.ShoppingCart;
import se.iths.projektarbetekomplexjava.service.*;

import java.util.Optional;

@RestController
@RequestMapping("bokhandel/api/v1/shoppingCart/")
public class ShoppingCartController {

    ShoppingCartService shoppingCartService;

    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @PostMapping("/createcart")
    public ResponseEntity<ShoppingCart> addShoppingCart(@RequestBody ShoppingCart shoppingCartRequest){
        ShoppingCart addShoppingCart = shoppingCartService.addingBookToShoppingCart(shoppingCartRequest);
        return new ResponseEntity<>(addShoppingCart, HttpStatus.CREATED);
    }

    @GetMapping("/getCartById")
    public ResponseEntity<Optional<ShoppingCart>> getShoppingCartById(@PathVariable Long id) {
        Optional<ShoppingCart> foundShoppingCart= shoppingCartService.getCartById(id);
        return new ResponseEntity<>(foundShoppingCart,HttpStatus.OK);

    }

}
