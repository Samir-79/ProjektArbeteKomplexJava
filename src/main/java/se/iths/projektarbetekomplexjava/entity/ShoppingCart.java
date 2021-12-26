package se.iths.projektarbetekomplexjava.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
    private Double totalPrice;
    private int totalQty;

    @JsonIgnore
    @OneToMany(targetEntity = BooksInShoppingCart.class, cascade = CascadeType.ALL)
    private List<BooksInShoppingCart> booksInShoppingCart = new ArrayList<>();





    @JsonIgnore
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToOne(fetch = FetchType.LAZY, targetEntity = Customer.class)
    private Customer customer;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Book> books = new HashSet<>();

    @JsonIgnore
    @OneToMany(targetEntity = Orders.class, cascade = CascadeType.ALL)
    private List<Orders> orders = new ArrayList<>();





    public ShoppingCart(Long id) {
        this.id = id;
    }

    public ShoppingCart() {
    }

    public void addBook(Book book) {
        books.add(book);
        book.getShoppingCart().add(this);
    }

    public void addCustomer(Customer customer) {
        this.customer = customer;
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



    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }


    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    public List<Orders> getOrders() {
        return orders;
    }

    public void setOrders(List<Orders> orders) {
        this.orders = orders;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(int totalQty) {
        this.totalQty = totalQty;
    }

    public List<BooksInShoppingCart> getBooksInShoppingCart() {
        return booksInShoppingCart;
    }

    public void setBooksInShoppingCart(List<BooksInShoppingCart> booksInShoppingCart) {
        this.booksInShoppingCart = booksInShoppingCart;
    }
}