package se.iths.projektarbetekomplexjava.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDateTime orderDate;
    private String shippingMethod;
    private String shippingAddress;
    private Double orderTotalPrice;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private List<OrderedBooks> orderedBooks= new ArrayList<>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "order")
    private List<CartItem> cartItemList;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Payment payment;

    public Orders(LocalDateTime orderDate, String shippingMethod, String shippingAddress, Double orderTotalPrice) {
        this.orderDate = orderDate;
        this.shippingMethod = shippingMethod;
        this.shippingAddress = shippingAddress;
        this.orderTotalPrice = orderTotalPrice;
    }

    public Orders() {
    }

    public void addToPayment(Payment payment) {
        setPayment(payment);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
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

    public void setOrderTotalPrice(Double orderTotalPrice) {
        this.orderTotalPrice = orderTotalPrice;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public List<CartItem> getCartItemList() {
        return cartItemList;
    }

    public void setCartItemList(List<CartItem> cartItemList) {
        this.cartItemList = cartItemList;
    }

    public List<OrderedBooks> getOrderedBooks() {
        return orderedBooks;
    }

    public void setOrderedBooks(List<OrderedBooks> orderedBooks) {
        this.orderedBooks = orderedBooks;
    }
}