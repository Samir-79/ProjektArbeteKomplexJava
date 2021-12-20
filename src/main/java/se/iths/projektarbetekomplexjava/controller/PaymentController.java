package se.iths.projektarbetekomplexjava.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.iths.projektarbetekomplexjava.entity.Payment;
import se.iths.projektarbetekomplexjava.entity.ShoppingCart;
import se.iths.projektarbetekomplexjava.service.PaymentService;

import java.util.Optional;

@RestController
@RequestMapping("bokhandel/api/v1/payment/")
public class PaymentController {
    PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/createCard")
    public ResponseEntity<Payment> createBankCard(@RequestBody Payment paymentRequest){
        Payment addedPayment=paymentService.createPayment(paymentRequest);
        return new ResponseEntity<>(addedPayment, HttpStatus.CREATED);

    }

    @GetMapping("/getCartById")
    public ResponseEntity<Optional<Payment>> getShoppingCartById(@PathVariable Long id) {
        Optional<Payment> foundPaymentCard= paymentService.getPaymentcardById(id);
        return new ResponseEntity<>(foundPaymentCard,HttpStatus.OK);

    }
}
