package se.iths.projektarbetekomplexjava.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import se.iths.projektarbetekomplexjava.entity.Employee;
import se.iths.projektarbetekomplexjava.entity.Role;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import java.util.Set;

public class EmployeePrincipal implements UserDetails {

    Employee employee;

    public EmployeePrincipal(Employee employee) {
        this.employee = employee;
    }

    //    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        Set<Role> roles = employee.getRoles();
//        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles.size());
//        for (Role role : roles) {
//            grantedAuthorities.add(new SimpleGrantedAuthority(role.getRole().toUpperCase()));
//        }
//        return grantedAuthorities;
//    }

    @Override
    public  Collection<? extends  GrantedAuthority> getAuthorities() {
        Role roles = employee.getRole();
        System.out.println(roles.toString());
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(roles.toString().toUpperCase()));
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return this.employee.getPassword();
    }

    @Override
    public String getUsername() {
        return this.employee.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}