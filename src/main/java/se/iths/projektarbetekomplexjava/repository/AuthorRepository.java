package se.iths.projektarbetekomplexjava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.iths.projektarbetekomplexjava.entity.Author;

import java.util.List;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {
    Author findByFirstNameAndLastName(String firstName, String lastName);
    List<Author> findByFirstName(String firstname);
    List<Author> findByLastName(String lastName);

}