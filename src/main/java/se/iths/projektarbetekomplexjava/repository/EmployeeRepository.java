package se.iths.projektarbetekomplexjava.repository;

import org.springframework.data.repository.CrudRepository;
import se.iths.projektarbetekomplexjava.entity.EmployeeEntity;

public interface EmployeeRepository extends CrudRepository<EmployeeEntity, Long> {
}
