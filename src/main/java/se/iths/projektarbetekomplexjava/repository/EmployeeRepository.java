package se.iths.projektarbetekomplexjava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.iths.projektarbetekomplexjava.entity.Employee;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {
    Employee findByUsername(String username);
    Employee findByUsernameAndPassword(String username, String password);
}
