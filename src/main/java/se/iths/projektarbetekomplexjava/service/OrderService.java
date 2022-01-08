package se.iths.projektarbetekomplexjava.service;

import org.springframework.stereotype.Service;
import se.iths.projektarbetekomplexjava.entity.*;
import se.iths.projektarbetekomplexjava.repository.OrderRepository;
import se.iths.projektarbetekomplexjava.repository.PaymentRepository;
import se.iths.projektarbetekomplexjava.repository.ShoppingCartRepository;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private OrderRepository orderRepository;
    private CartItemService cartItemService;
    private ShoppingCartService shoppingCartService;

    public OrderService(OrderRepository orderRepository,
                        CartItemService cartItemService,
                        ShoppingCartService shoppingCartService)
    {
        this.orderRepository = orderRepository;
        this.cartItemService = cartItemService;
        this.shoppingCartService = shoppingCartService;
    }

    public Orders createOrder(Orders order,ShoppingCart shoppingCart){
        List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);

        for (CartItem cart:cartItemList) {
            cart.setOrder(order);
        }
        // order.aPayment(order.getPayment());
        order.addToPayment(order.getPayment());
        order.setOrderTotalPrice(shoppingCart.getGrandTotal());
        order.setCartItemList(cartItemList);
        try {
            return orderRepository.save(order);
        }finally {
            shoppingCartService.clearShoppingCart(shoppingCart);
        }
    }

    public Optional<Orders> findOrderById(Long id){
        return orderRepository.findById(id);
    }
}