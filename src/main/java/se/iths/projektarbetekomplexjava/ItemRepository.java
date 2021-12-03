package se.iths.projektarbetekomplexjava;

import org.springframework.data.repository.CrudRepository;

public interface ItemRepository  extends CrudRepository<ItemEntity, Long> {
}
