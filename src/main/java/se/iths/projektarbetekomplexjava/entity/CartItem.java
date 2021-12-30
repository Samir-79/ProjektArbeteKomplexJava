package se.iths.projektarbetekomplexjava.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int qty;
    private Double subtotal;

    @OneToOne
    private Book book;

    @OneToMany(mappedBy = "cartItem", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<BookToCart> bookToCart;

    @ManyToOne(cascade = CascadeType.ALL)
    private Orders order;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(cascade = CascadeType.PERSIST)
    private ShoppingCart shoppingCart;

    public void addToOrder(Orders orders) {
        setOrder(orders);
        orders.getCartItemList().add(this);
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

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public List<BookToCart> getBookToCart() {
        return bookToCart;
    }

    public void setBookToCartOfBooks(List<BookToCart> bookToCartList) {
        this.bookToCart = bookToCartList;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public void setBookToCart(List<BookToCart> bookToCart) {
        this.bookToCart = bookToCart;
    }

    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }
}
