package se.iths.projektarbetekomplexjava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.iths.projektarbetekomplexjava.entity.ERole;

public interface Role {

    @Repository
    public interface RoleRepository extends JpaRepository<Role, Integer> {
        Role findByName(ERole name);
    }
}
