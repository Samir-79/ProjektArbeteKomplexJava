package se.iths.projektarbetekomplexjava.entity;

import javax.persistence.*;
import java.util.Date;


@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    //@Temporal(TemporalType.DATE)
    private String orderDate;
    private String shippingMethod;
    private String shippingAddress;
    private Double orderTotalPrice;

    @ManyToOne
    private ShoppingCart shoppingCart;
    @OneToOne
     private Payment payment;

    public Order( String orderDate, String shippingMethod, String shippingAddress, Double orderTotalPrice) {

        this.orderDate = orderDate;
        this.shippingMethod = shippingMethod;
        this.shippingAddress = shippingAddress;
        this.orderTotalPrice = orderTotalPrice;
    }

    public Order() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public Double getOrderTotalPrice() {
        return orderTotalPrice;
    }

    public void setOrderTotalPrice(Double orderTotal) {
        this.orderTotalPrice = orderTotal;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}
