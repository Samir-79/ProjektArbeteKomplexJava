package se.iths.projektarbetekomplexjava.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Set;

@Entity
public class Book {



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public void add(Book book) {
    }
}
