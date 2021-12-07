package se.iths.projektarbetekomplexjava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.iths.projektarbetekomplexjava.entity.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {
}
