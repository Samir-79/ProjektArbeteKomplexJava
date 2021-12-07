package se.iths.projektarbetekomplexjava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.iths.projektarbetekomplexjava.entity.Book;

public interface BookRepository extends JpaRepository<Book,Long> {
}
