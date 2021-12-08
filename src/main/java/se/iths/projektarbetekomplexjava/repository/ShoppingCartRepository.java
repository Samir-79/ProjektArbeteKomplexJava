package se.iths.projektarbetekomplexjava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.iths.projektarbetekomplexjava.entity.ShoppingCart;

@Repository
public interface ShoppingCartRepository extends  CrudRepository<ShoppingCart, Long> {
}