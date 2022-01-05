package se.iths.projektarbetekomplexjava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.iths.projektarbetekomplexjava.entity.LoggedIn;

@Repository
public interface LogInRepository extends CrudRepository<LoggedIn, Long> {
    LoggedIn findByCustomer_Id(Long customer_id);
}
