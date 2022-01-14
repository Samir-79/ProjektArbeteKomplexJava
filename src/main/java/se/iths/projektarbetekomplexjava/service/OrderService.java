package se.iths.projektarbetekomplexjava.service;

import org.springframework.stereotype.Service;
import se.iths.projektarbetekomplexjava.entity.*;
import se.iths.projektarbetekomplexjava.exception.NotFoundException;
import se.iths.projektarbetekomplexjava.repository.OrderRepository;
import se.iths.projektarbetekomplexjava.repository.PaymentRepository;
import se.iths.projektarbetekomplexjava.repository.ShoppingCartRepository;
import se.iths.projektarbetekomplexjava.repository.StockRepository;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartItemService cartItemService;
    private final ShoppingCartService shoppingCartService;
    private final StockRepository stockRepository;


    public OrderService(OrderRepository orderRepository,
                        CartItemService cartItemService,
                        ShoppingCartService shoppingCartService,
                        StockRepository stockRepository) {
        this.orderRepository = orderRepository;
        this.cartItemService = cartItemService;
        this.shoppingCartService = shoppingCartService;
        this.stockRepository = stockRepository;
    }

    public Orders createOrder(Orders order, ShoppingCart shoppingCart) {
        List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);
        if (cartItemList.isEmpty()) {
            throw new NotFoundException("No books in shopping cart with id: " + shoppingCart.getId());
        }
        Stock stock;



        for (CartItem cart : cartItemList) {
            cart.setOrder(order);
            stock = cart.getBook().getStock();
            stock.setQuantity(stock.getQuantity() - cart.getQty());
            OrderedBooks orderedBook = new OrderedBooks(cart.getBook().getISBN13(),cart.getQty());
            System.out.println(orderedBook.getISBN13());
            orderedBook.addOrderedBooks(order);

        }

        // order.aPayment(order.getPayment());
        order.addToPayment(order.getPayment());
        order.setOrderTotalPrice(shoppingCart.getGrandTotal());

        order.setCartItemList(cartItemList);
        //
        try {
            return orderRepository.save(order);
        } finally {
            shoppingCartService.clearShoppingCart(shoppingCart);
        }
    }

    public Orders findOrderById(Long id) {
        Orders foundOrder = orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Order not found"));
        return foundOrder;
    }

    public void removeOrder(Long id) {
        Orders foundOrder = orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Order not found"));
        orderRepository.deleteById(foundOrder.getId());

    }

    public List<Orders> findAllOrders() {
        return (List<Orders>) orderRepository.findAll();
    }

    public Orders updateOrder(Orders orders) {
        findOrderById(orders.getId());
        return orderRepository.save(orders);
    }
}