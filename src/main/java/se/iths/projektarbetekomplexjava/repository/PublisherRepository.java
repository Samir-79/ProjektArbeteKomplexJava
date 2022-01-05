package se.iths.projektarbetekomplexjava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.iths.projektarbetekomplexjava.entity.Publisher;

@Repository
public interface PublisherRepository  extends CrudRepository<Publisher,Long>{
    Publisher findByName(String name);
}