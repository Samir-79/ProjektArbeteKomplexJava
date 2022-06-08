package se.iths.projektarbetekomplexjava.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
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

import java.io.IOException;
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

    public String getUserName(String authorization) {
        String authorizationHeader = authorization;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            //try {
            //delete the bearer from header
            String access_token = authorizationHeader.substring("Bearer ".length());
            //should be the same with the algorithm that add to the token during creation,
            Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
            //verify the token using the algorithm
            JWTVerifier verifier = JWT.require(algorithm).build();
            //verify the token is valid
            DecodedJWT decodedJWT = verifier.verify(access_token);
            //get the username from the subject
            String username = decodedJWT.getSubject();
            //get the user from DB using the username we get from the token
            //uAppUser user = service.getUser(username);
            return username;
        }

        else {
            throw new RuntimeException("JWT Token is missing");
        }
    }
}
