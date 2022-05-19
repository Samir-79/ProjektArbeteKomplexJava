package se.iths.projektarbetekomplexjava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.iths.projektarbetekomplexjava.entity.ERole;
import se.iths.projektarbetekomplexjava.entity.Role;

import java.util.Optional;


@Repository
    public interface RoleRepository extends JpaRepository <Role, Integer> {
        Optional<Role> findByName(ERole name);
    }
