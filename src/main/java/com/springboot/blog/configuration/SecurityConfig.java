package com.springboot.blog.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {


    private UserDetailsService userService;

    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userService = userDetailsService;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable().
                authorizeHttpRequests(
                        // Permission for all users to access to all GET Endpoints
                        authorized -> authorized.requestMatchers(HttpMethod.GET,"/api/**").permitAll()
                                                .requestMatchers("/api/auth/**").permitAll()
                                // Apart from above methods, all requests should be authenticated
                                .anyRequest().authenticated()
                )//authorized.anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults());
        return httpSecurity.build();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

//    @Bean
//    public UserDetailsService userDetails() {
//        UserDetails prasad = User.builder().username("Prasad").password(passwordEncoder().encode("Prasad123")).roles("USER").build();
//        UserDetails admin = User.builder().username("Admin").password(passwordEncoder().encode("Admin123")).roles("ADMIN").build();
//        return new InMemoryUserDetailsManager(prasad, admin);
//    }
}
