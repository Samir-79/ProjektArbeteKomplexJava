package se.iths.projektarbetekomplexjava.service;

import org.springframework.stereotype.Service;
import se.iths.projektarbetekomplexjava.entity.Order;
import se.iths.projektarbetekomplexjava.repository.OrderRepository;
import se.iths.projektarbetekomplexjava.repository.PaymentRepository;
import se.iths.projektarbetekomplexjava.repository.ShoppingCartRepository;

@Service
public class OrderService {
    private OrderRepository orderRepository;
    private ShoppingCartRepository shoppingCartRepository;
    private PaymentRepository paymentRepository;

    public OrderService(OrderRepository orderRepository,
                        ShoppingCartRepository shoppingCartRepository,
                        PaymentRepository paymentRepository)
    {
        this.orderRepository = orderRepository;
        this.shoppingCartRepository = shoppingCartRepository;
        this.paymentRepository = paymentRepository;
    }

    public Order createOrder(){
        return null;
    }
}
