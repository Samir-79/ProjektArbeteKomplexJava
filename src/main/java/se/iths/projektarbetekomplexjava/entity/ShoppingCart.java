package se.iths.projektarbetekomplexjava.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int bookQuantity;

    @OneToMany(mappedBy="shoppingCart",cascade= CascadeType.ALL)
    private List<Book> books= new ArrayList<>();

    public ShoppingCart(int bookQuantity) {
        this.bookQuantity = bookQuantity;
    }

    public ShoppingCart() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getBookQuantity() {
        return bookQuantity;
    }

    public void setBookQuantity(int bookQuantity) {
        this.bookQuantity = bookQuantity;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
