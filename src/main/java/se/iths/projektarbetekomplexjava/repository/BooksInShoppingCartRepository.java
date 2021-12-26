package se.iths.projektarbetekomplexjava.repository;

import org.springframework.stereotype.Repository;
import se.iths.projektarbetekomplexjava.entity.BooksInShoppingCart;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface BooksInShoppingCartRepository extends  CrudRepository <BooksInShoppingCart,Long>{

 BooksInShoppingCart findByIsbn13(String isbn13);

}
