package se.iths.projektarbetekomplexjava.repository;

import org.springframework.data.repository.CrudRepository;
import se.iths.projektarbetekomplexjava.entity.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {
    Employee findByUsername(String username);
    Employee findByUsernameAndPassword(String username, String password);
}
