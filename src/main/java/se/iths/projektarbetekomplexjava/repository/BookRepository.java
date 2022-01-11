package se.iths.projektarbetekomplexjava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.iths.projektarbetekomplexjava.entity.Author;
import se.iths.projektarbetekomplexjava.entity.Book;

import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<Book,Long> {
    Book findByISBN13(String ISBN13);
    List<Book> findByTitle(String title);
    List<Book> findByLanguage(String language);
    List<Book> findByAuthors(Author author);
}