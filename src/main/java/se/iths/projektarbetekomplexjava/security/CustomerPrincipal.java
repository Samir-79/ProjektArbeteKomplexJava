package se.iths.projektarbetekomplexjava.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import se.iths.projektarbetekomplexjava.entity.Customer;
import se.iths.projektarbetekomplexjava.entity.Role;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public class CustomerPrincipal implements UserDetails {
        Customer customer;

    public CustomerPrincipal(Customer customer) {
        this.customer = customer;
    }
    //    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        Set<Role> roles = customer.getRoles();
//        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles.size());
//        for (Role role : roles) {
//            grantedAuthorities.add(new SimpleGrantedAuthority(role.getRole().toUpperCase()));
//        }
//        return grantedAuthorities;
//    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Role roles = customer.getRole();
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(roles.toString()));
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return this.customer.getPassword();
    }

    @Override
    public String getUsername() {
        return this.customer.getUsername();
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
