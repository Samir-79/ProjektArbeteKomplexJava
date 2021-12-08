package se.iths.projektarbetekomplexjava.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Book {



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public void add(Book book) {
    }
    @ManyToOne
    ShoppingCart shoppingCart;
}
