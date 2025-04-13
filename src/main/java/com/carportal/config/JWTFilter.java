package com.carportal.config;

import com.carportal.entity.User;
import com.carportal.repository.UserRepository;
import com.carportal.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Component
public class JWTFilter extends OncePerRequestFilter {

    private UserRepository  userRepository;
    private JWTService jwtService;

    JWTFilter(UserRepository userRepository, JWTService jwtService){
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = request.getHeader("Authorization");
        if(token != null&& token.startsWith("Bearer ")){
            // Extract the token from the Authorization header
            String jwtToken = token.substring(7, token.length());
            // Validate the token and extract user details
            String username = jwtService.getUsername(jwtToken);
            System.out.println(username);
            Optional<User> opUser = userRepository.findByUsername(username);
            if(opUser.isPresent()) {
                User user = opUser.get();
                UsernamePasswordAuthenticationToken authenticationToken
                        = new UsernamePasswordAuthenticationToken(user, null,
                        Collections.singleton(new SimpleGrantedAuthority(user.getRole())));
                authenticationToken.setDetails(new WebAuthenticationDetails(request));
                // Set the authentication in the SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            }
        }filterChain.doFilter(request, response);


    }


}

