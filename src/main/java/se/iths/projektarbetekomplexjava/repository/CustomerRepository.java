package se.iths.projektarbetekomplexjava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.iths.projektarbetekomplexjava.entity.Customer;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
    Customer findByUsername(String username);
    Optional<Customer> findCustomerByEmailAndPassword(String email, String password);
    List<Customer> findCustomerByEmail(String email);
}