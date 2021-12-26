package se.iths.projektarbetekomplexjava.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
public class BooksInShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String isbn13;
    private int numberOfCopies;
    private Long priceCopies;


//    @JsonIgnore
//    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//    @OneToOne(fetch = FetchType.LAZY, targetEntity = Customer.class)
//    private Customer customer;



    @ManyToOne(cascade = CascadeType.MERGE)
    ShoppingCart shoppingCart;

    public BooksInShoppingCart() {
    }

    public BooksInShoppingCart(String isbn13, int numberOfCopies, Long priceCopies) {
        this.isbn13 = isbn13;
        this.numberOfCopies = numberOfCopies;
        this.priceCopies = priceCopies;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumberOfCopies() {
        return numberOfCopies;
    }

    public void setNumberOfCopies(int numberOfCopies) {
        this.numberOfCopies = numberOfCopies;
    }

    public Long getPriceCopies() {
        return priceCopies;
    }

    public void setPriceCopies(Long priceCopies) {
        this.priceCopies = priceCopies;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }


}
