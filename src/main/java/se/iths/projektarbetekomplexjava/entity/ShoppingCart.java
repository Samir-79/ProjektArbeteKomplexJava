package se.iths.projektarbetekomplexjava.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Double price;
    private int qty;

    @OneToOne(cascade = CascadeType.ALL)
    private Customer customer;


    @OneToMany(targetEntity = Book.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "book_shoppingCart", referencedColumnName = "id")
    private List<Book> books = new ArrayList<>();
    @OneToMany( cascade = CascadeType.ALL,mappedBy = "shoppingCart")
   // @JoinColumn(name = "order_shoppingCart", referencedColumnName = "id")
    private List<Order> orders = new ArrayList<>();

    public ShoppingCart(Double price, int qty) {
        this.price = price;
        this.qty = qty;
    }

    public ShoppingCart() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
