package se.iths.projektarbetekomplexjava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.iths.projektarbetekomplexjava.entity.CartItem;
import se.iths.projektarbetekomplexjava.entity.ShoppingCart;

import java.util.List;

@Repository
public interface CartItemRepository extends CrudRepository<CartItem, Long> {
    List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);
    void deleteCartItemByShoppingCart(ShoppingCart shoppingCart);

   // List<CartItem> findByOrder(Order order);
}
