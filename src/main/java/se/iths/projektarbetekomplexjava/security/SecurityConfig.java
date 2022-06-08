package se.iths.projektarbetekomplexjava.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import se.iths.projektarbetekomplexjava.filter.CorsFilter;
import se.iths.projektarbetekomplexjava.filter.CustomAuthenticationFilter;
import se.iths.projektarbetekomplexjava.filter.CustomAuthorizationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;


@Configurable @EnableWebSecurity @RequiredArgsConstructor

public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter= new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthenticationFilter.setFilterProcessesUrl("/bokhandel/api/v1/user/login");
        http.cors().disable();
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        http.authorizeRequests().antMatchers("/bokhandel/api/v1/user/signup/**").permitAll();
        http.authorizeRequests().antMatchers("/bokhandel/api/v1/user/login").permitAll();;
        http.authorizeRequests().antMatchers("/bokhandel/api/v1/user/token/refresh").permitAll();
        //http.authorizeRequests().antMatchers(GET,"/api/user/**").hasAnyAuthority("ROLE_USER");
        http.authorizeRequests().antMatchers("/bokhandel/api/v1/shoppingcart/**").hasRole("USER");
        http.authorizeRequests().antMatchers("/bokhandel/api/vi/orders/**").hasRole("USER");
        http.authorizeRequests().antMatchers("/bokhandel/api/v1/book/addbook").hasRole("ADMIN");
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(customAuthenticationFilter);
        //this comes before all filters.
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(new CorsFilter(), ChannelProcessingFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Arrays.asList("*"));
//        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
//        configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
//        configuration.setExposedHeaders(Arrays.asList("x-auth-token"));
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }


}

