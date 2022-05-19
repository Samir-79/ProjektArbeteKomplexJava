package se.iths.projektarbetekomplexjava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.iths.projektarbetekomplexjava.entity.AppUser;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Integer> {


    AppUser findByUserName(String userName);

}
