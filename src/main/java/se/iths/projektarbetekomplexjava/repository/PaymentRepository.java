package se.iths.projektarbetekomplexjava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.iths.projektarbetekomplexjava.entity.Payment;

@Repository
public interface PaymentRepository extends CrudRepository<Payment,Long> {
}