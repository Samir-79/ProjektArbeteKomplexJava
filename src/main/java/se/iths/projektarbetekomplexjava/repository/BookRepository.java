package se.iths.projektarbetekomplexjava.repository;

import org.springframework.data.repository.CrudRepository;
import se.iths.projektarbetekomplexjava.entity.Book;

public interface BookRepository extends CrudRepository<Book,Long> {
}
