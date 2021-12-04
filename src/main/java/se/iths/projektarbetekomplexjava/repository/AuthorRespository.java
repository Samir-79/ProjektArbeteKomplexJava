package se.iths.projektarbetekomplexjava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.iths.projektarbetekomplexjava.entity.Author;

@Repository
public interface AuthorRespository extends CrudRepository<Author, Long> {
}
