package se.iths.projektarbetekomplexjava.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String ISBN13;
    private String title;
    private LocalDate publishingDate;
    private Long weight;
    private int pages;
    private String language;
    private String category;
    private Long price;

    @ManyToMany(mappedBy = "books")
    private Set<Author> authors = new HashSet<>();

    @ManyToOne(cascade = CascadeType.PERSIST)
    Publisher publisher;

    @ManyToOne(cascade = CascadeType.ALL)
    Stock stock;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<BookToCart> bookToCart;

    public Book( String ISBN13, String title, LocalDate publishingDate, Long weight, int pages, String language, String category, Long price) {

        this.ISBN13 = ISBN13;
        this.title = title;
        this.publishingDate = publishingDate;
        this.weight = weight;
        this.pages = pages;
        this.language = language;
        this.category = category;
        this.price = price;

    }

    public Book() {
    }

    public void addAuthor(Author author) {
        authors.add(author);
        author.getBooks().add(this);
    }

    public void addPublisher(Publisher publisher) {
        setPublisher(publisher);
        publisher.getBooks().add(this);
    }

    public void addToStock(Stock stock) {
        setStock(stock);
        stock.getBooks().add(this);
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getPublishingDate() {
        return publishingDate;
    }

    public void setPublishingDate(LocalDate publishingDate) {
        this.publishingDate = publishingDate;
    }

    public Long getWeight() {
        return weight;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public List<BookToCart> getBookToCart() {
        return bookToCart;
    }

    public void setBookToCart(List<BookToCart> bookToCart) {
        this.bookToCart = bookToCart;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", ISBN13='" + ISBN13 + '\'' +
                ", title='" + title + '\'' +
                ", publishingDate=" + publishingDate +
                ", weight=" + weight +
                ", pages=" + pages +
                ", language='" + language + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", authors=" + authors +
                ", publisher=" + publisher +
                ", stock=" + stock +
                ", bookToCart=" + bookToCart +
                '}';
    }
}