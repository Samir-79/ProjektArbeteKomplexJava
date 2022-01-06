package se.iths.projektarbetekomplexjava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.iths.projektarbetekomplexjava.entity.Employee;

import java.util.List;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {
    Employee findByUsername(String username);
    List<Employee> findEmployeesByEmail(String email);
    Employee findEmployeeByEmail(String email);
}
