package se.iths.projektarbetekomplexjava.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import se.iths.projektarbetekomplexjava.entity.CartItem;
import se.iths.projektarbetekomplexjava.entity.ShoppingCart;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends CrudRepository<CartItem, Long> {
    List<CartItem> findByShoppingCart(Optional<ShoppingCart> shoppingCart);

    void deleteCartItemByShoppingCart(ShoppingCart shoppingCart);


   @Query(value = "SELECT * FROM cart_item  crt JOIN shopping_cart sh ON crt.shopping_cart_id=sh.id JOIN  customer c ON sh.customer_id= c.id  AND  c.username=:username AND crt.book_id=:bookid ",nativeQuery = true)
   CartItem findCartItemByUserNameAndBookId(
           @Param("username") String username,
            @Param("bookid") Long bookid

   );



}