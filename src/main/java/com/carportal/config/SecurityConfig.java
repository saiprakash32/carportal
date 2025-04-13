package com.carportal.config;

import jakarta.servlet.Filter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
public class SecurityConfig {
    private JWTFilter   jwtFilter;


    SecurityConfig(JWTFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) // Disable CSRF using lambda
            .cors(cors -> cors.disable());  // Disable CORS (not recommended, configure instead)
        http.addFilterBefore(jwtFilter, AuthorizationFilter.class); // Add JWT filter before authorization filter

        http.authorizeHttpRequests(auth->auth
                    .requestMatchers("/api/v1/auth/user/signup","/api/v1/auth/login", "/api/v1/auth/owner/signup").permitAll()
                .requestMatchers("/api/v1/car").hasRole("USER")
                    .anyRequest().authenticated());
            return http.build();
        }

    }
