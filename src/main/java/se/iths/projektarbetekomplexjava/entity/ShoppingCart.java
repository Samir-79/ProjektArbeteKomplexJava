package se.iths.projektarbetekomplexjava.entity;
//import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Double GrandTotal;

    @OneToMany(mappedBy="shoppingCart", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    @JsonIgnore
    private List<CartItem> cartOfBooks;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToOne(cascade = CascadeType.PERSIST)
    @JsonIgnore
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

    public List<CartItem> getCartOfBook() {
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


   /* @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Double price;
    private int qty;



    // @Cascade({org.hibernate.annotations.CascadeType.PERSIST,org.hibernate.annotations.CascadeType.SAVE_UPDATE})
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

    public ShoppingCart(Double price, int qty) {
        this.price = price;
        this.qty = qty;
    }

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
    }*/
}