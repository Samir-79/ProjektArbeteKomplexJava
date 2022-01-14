package se.iths.projektarbetekomplexjava.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
import se.iths.projektarbetekomplexjava.entity.OrderedBooks;

@Repository
public interface OrderedBooksRepository  extends CrudRepository<OrderedBooks,Long>{

}
