package se.iths.projektarbetekomplexjava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import se.iths.projektarbetekomplexjava.entity.BookToCart;
import se.iths.projektarbetekomplexjava.entity.CartItem;



@Repository
@Transactional
public interface BookToCartRepository extends CrudRepository<BookToCart,Long> {
    void deleteByCartItem(CartItem cartItem);
}
