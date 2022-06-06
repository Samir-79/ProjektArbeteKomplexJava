package se.iths.projektarbetekomplexjava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.iths.projektarbetekomplexjava.entity.CartItem;
import se.iths.projektarbetekomplexjava.entity.ShoppingCart;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends CrudRepository<CartItem, Long> {
    List<CartItem> findByShoppingCart(Optional<ShoppingCart> shoppingCart);

    void deleteCartItemByShoppingCart(ShoppingCart shoppingCart);
}