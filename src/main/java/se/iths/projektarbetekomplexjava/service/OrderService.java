package se.iths.projektarbetekomplexjava.service;

import org.springframework.stereotype.Service;
import se.iths.projektarbetekomplexjava.entity.Orders;
import se.iths.projektarbetekomplexjava.entity.Payment;
import se.iths.projektarbetekomplexjava.entity.ShoppingCart;
import se.iths.projektarbetekomplexjava.repository.OrderRepository;
import se.iths.projektarbetekomplexjava.repository.PaymentRepository;
import se.iths.projektarbetekomplexjava.repository.ShoppingCartRepository;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private OrderRepository orderRepository;
    private ShoppingCartRepository shoppingCartRepository;
    private PaymentRepository paymentRepository;

    public OrderService(OrderRepository orderRepository,
                        ShoppingCartRepository shoppingCartRepository,
                        PaymentRepository paymentRepository) {
        this.orderRepository = orderRepository;
        this.shoppingCartRepository = shoppingCartRepository;
        this.paymentRepository = paymentRepository;
    }

    public Orders createOrder(ShoppingCart shoppingCart, Payment payment, Orders order) {
       /* Orders orders=new Orders();
        orders.setShoppingCart(shoppingCart);
        orders.setPayment(payment);
        orders.setOrderDate(order.getOrderDate());
        orders.setShippingAddress(order.getShippingAddress());
        orders.setShippingMethod(order.getShippingMethod());*/
        return orderRepository.save(order);
    }

    public Optional<Orders> findOrderById(Long id) {
        return orderRepository.findById(id);

    }
}
