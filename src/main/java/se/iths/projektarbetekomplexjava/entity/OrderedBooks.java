package se.iths.projektarbetekomplexjava.entity;

import javax.persistence.*;

@Entity
public class OrderedBooks {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String ISBN13;
    private int numCopies;

    @ManyToOne(cascade = CascadeType.ALL)
    Orders order;

    public OrderedBooks(String ISBN13, int numCopies) {
        this.ISBN13 = ISBN13;
        this.numCopies = numCopies;
    }

    public OrderedBooks() {
    }

    public  void addOrderedBooks(Orders order){
        setOrders(order);
        order.getOrderedBooks().add(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getISBN13() {
        return ISBN13;
    }

    public void setISBN13(String ISBN13) {
        this.ISBN13 = ISBN13;
    }

    public int getNumCopies() {
        return numCopies;
    }

    public void setNumCopies(int numCopies) {
        this.numCopies = numCopies;
    }

    public Orders getOrders() {
        return order;
    }

    public void setOrders(Orders order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return
                "ISBN13='" + ISBN13 + '\'' +
                ", numCopies=" + numCopies +
                '}';
    }
}