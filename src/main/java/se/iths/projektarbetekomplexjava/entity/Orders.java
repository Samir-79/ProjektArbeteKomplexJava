package se.iths.projektarbetekomplexjava.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    //@Temporal(TemporalType.DATE)
    private String orderDate;
    private String shippingMethod;
    private String shippingAddress;
    private Double orderTotalPrice;

    @OneToMany( cascade = CascadeType.REMOVE, mappedBy = "order")
    @JsonIgnore
    private List<CartItem> cartItemList;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Payment payment;

    public Orders(String orderDate, String shippingMethod, String shippingAddress, Double orderTotalPrice) {
        this.orderDate = orderDate;
        this.shippingMethod = shippingMethod;
        this.shippingAddress = shippingAddress;
        this.orderTotalPrice = orderTotalPrice;
    }

    public Orders() {
    }

    public void addToPayment(Payment payment) {
        setPayment(payment);
        payment.setOrder(this);
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
}