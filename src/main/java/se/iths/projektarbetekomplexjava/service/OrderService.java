package se.iths.projektarbetekomplexjava.service;

import org.springframework.stereotype.Service;
import se.iths.projektarbetekomplexjava.entity.*;
import se.iths.projektarbetekomplexjava.repository.CartItemRepository;
import se.iths.projektarbetekomplexjava.repository.OrderRepository;
import se.iths.projektarbetekomplexjava.repository.PaymentRepository;
import se.iths.projektarbetekomplexjava.repository.ShoppingCartRepository;

import javax.persistence.criteria.Order;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private OrderRepository orderRepository;
    private ShoppingCartRepository shoppingCartRepository;
    private PaymentRepository paymentRepository;
    private CartItemService cartItemService;

    public OrderService(OrderRepository orderRepository,
                        ShoppingCartRepository shoppingCartRepository,
                        PaymentRepository paymentRepository,CartItemService cartItemService)
    {
        this.orderRepository = orderRepository;
        this.shoppingCartRepository = shoppingCartRepository;
        this.paymentRepository = paymentRepository;
        this.cartItemService=cartItemService;
    }

    public Orders createOrder(Orders order,ShoppingCart shoppingCart){
        List<CartItem> cartItemList=cartItemService.findByShoppingCart(shoppingCart);

        for (CartItem cart:cartItemList) {
           cart.setOrder(order);
        }
        order.setPayment(order.getPayment());
        order.setOrderTotalPrice(shoppingCart.getGrandTotal());
        order.setCartItemList(cartItemList);

        return orderRepository.save(order);

    }

    public Optional<Orders> findOrderById(Long id){
       return orderRepository.findById(id);

    }
}
