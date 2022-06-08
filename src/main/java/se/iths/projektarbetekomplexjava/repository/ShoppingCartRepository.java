package se.iths.projektarbetekomplexjava.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import se.iths.projektarbetekomplexjava.entity.ShoppingCart;

import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, Long> {

    @Query(value="SELECT * FROM shopping_cart sh JOIN   customer c ON sh.customer_id= c.id AND c.username=:username", nativeQuery = true)
    ShoppingCart findShoppingCartByUserName(
            @Param("username") String username
    );
}

