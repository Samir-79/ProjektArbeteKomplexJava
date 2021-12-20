package se.iths.projektarbetekomplexjava.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


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
    private List<Book> books = new ArrayList<>();

    @OneToMany(targetEntity = Orders.class, cascade = CascadeType.ALL)
    private List<Orders> orders = new ArrayList<>();

    public ShoppingCart(Double price, int qty) {
        this.price = price;
        this.qty = qty;
    }

    public ShoppingCart() {
    }

    public void addBook(Book book) {
        books.add(book);
        book.setShoppingCart(this);
    }

    public void addCustomer(Customer customer) {
        this.customer=customer;
        customer.setShoppingCart(this);
    }

    public void addOrder(Orders order) {
        orders.add(order);
        order.setShoppingCart(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
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

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public List<Orders> getOrders() {
        return orders;
    }

    public void setOrders(List<Orders> orders) {
        this.orders = orders;
    }
}