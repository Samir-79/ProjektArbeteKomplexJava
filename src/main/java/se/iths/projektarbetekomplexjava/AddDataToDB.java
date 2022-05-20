package se.iths.projektarbetekomplexjava;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import se.iths.projektarbetekomplexjava.entity.ERole;
import se.iths.projektarbetekomplexjava.entity.Role;
import se.iths.projektarbetekomplexjava.repository.RoleRepository;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Configuration
@Data
public class AddDataToDB {

    private RoleRepository roleRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void addToDatabase() {
        roleRepository.saveAll(generateRoles());
    }

    private static List<Role> generateRoles() {
        List<Role> roles = new ArrayList<>();
        Role roleUser = new Role(ERole.ROLE_USER);
        Role roleAdmin= new Role(ERole.ROLE_ADMIN);
        roles.add(roleUser);
        roles.add(roleAdmin);
        return roles;

    }
}
