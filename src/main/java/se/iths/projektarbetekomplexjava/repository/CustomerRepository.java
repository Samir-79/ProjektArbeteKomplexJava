package se.iths.projektarbetekomplexjava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.iths.projektarbetekomplexjava.entity.Customer;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
    Optional<Customer> findByUsername(String username);
    List<Customer> findCustomersByEmail(String email);
}