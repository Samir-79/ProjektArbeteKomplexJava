package se.iths.projektarbetekomplexjava.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.iths.projektarbetekomplexjava.entity.AppUser;
import se.iths.projektarbetekomplexjava.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public AppUser getUser(String username) {
        return userRepository.findByUserName(username);
    }


    public AppUser save(AppUser appUser){
        return userRepository.save(appUser);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepository.findByUserName(username);
        if (user == null) {
            log.info("user not found");
        } else
            log.info("user found in the database:{}", username);
        //adding the role we create in to SimpleGrantedAuthority()
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName().toString()));
        });

        return new User(user.getUserName(), user.getPassword(), authorities);
    }
}
