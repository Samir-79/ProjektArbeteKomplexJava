package se.iths.projektarbetekomplexjava.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Double GrandTotal;
    private int totalNumberOfBooks;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToMany(mappedBy = "shoppingCart", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<CartItem> cartOfBooks;

    @JsonIgnore
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToOne(fetch = FetchType.EAGER, targetEntity = Customer.class)
    private Customer customer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getGrandTotal() {
        return GrandTotal;
    }

    public void setGrandTotal(Double grandTotal) {
        GrandTotal = grandTotal;
    }

    public int getTotalNumberOfBooks() {
        return totalNumberOfBooks;
    }

    public void setTotalNumberOfBooks(int totalNumberOfBooks) {
        this.totalNumberOfBooks = totalNumberOfBooks;
    }

    public List<CartItem> getCartOfBooks() {
        return cartOfBooks;
    }

    public void setCartOfBooks(List<CartItem> cartItemList) {
        this.cartOfBooks = cartItemList;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}