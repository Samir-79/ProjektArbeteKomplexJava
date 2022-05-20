package se.iths.projektarbetekomplexjava.security;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import se.iths.projektarbetekomplexjava.entity.AppUser;
import se.iths.projektarbetekomplexjava.repository.UserRepository;

import javax.transaction.Transactional;

@Service
public class UserDetailService implements  UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailService(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = userRepository.findByUserName(username);
        if (appUser== null){
            throw new UsernameNotFoundException("Can't find user with username: " + "username");
        }
        return new UserPrincipal(appUser);
    }
}
